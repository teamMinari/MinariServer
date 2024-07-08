package Minari.cheongForDo.domain.member.service;


import Minari.cheongForDo.domain.member.authority.MemberAccountType;
import Minari.cheongForDo.domain.member.entity.MemberEntity;
import Minari.cheongForDo.domain.member.presentation.dto.MemberLoginDTO;
import Minari.cheongForDo.domain.member.presentation.dto.MemberResponseDTO;
import Minari.cheongForDo.domain.member.repository.MemberRepository;
import Minari.cheongForDo.domain.member.presentation.dto.MemberRegisterDTO;
import Minari.cheongForDo.global.auth.JwtUtils;
import Minari.cheongForDo.global.exception.CustomErrorCode;
import Minari.cheongForDo.global.exception.CustomException;
import Minari.cheongForDo.global.response.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static Minari.cheongForDo.global.exception.CustomErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtils jwtUtils;

    public BaseResponse<?> postRegister(MemberRegisterDTO dto) {

        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new CustomException(CustomErrorCode.PASSWORDS_DO_NOT_MATCH);
        }

        if (memberRepository.existsById(dto.getId())) {
            throw new CustomException(CustomErrorCode.MEMBER_ALREADY_EXIST);
        }

        memberRepository.save(
                MemberEntity.builder()
                        .id(dto.getId())
                        .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                        .email(dto.getEmail())
                        .point(dto.getPoint() != null ? dto.getPoint() : 0L)
                        .exp(dto.getExp() != null ? dto.getExp() : 0L)
                        .authority(MemberAccountType.ROLE_USER)
                        .title(dto.getTitle())
                        .level(dto.getLevel() != null ? dto.getLevel() : 1L)
                        .build()
        );

        return BaseResponse.of(
                true,
                "OK",
                "회원가입 성공",
                null
        );
    }

    public BaseResponse<?> postLogin(MemberLoginDTO dto) {
        MemberEntity member = memberRepository.findById(dto.getId())
                .orElseThrow(() -> new CustomException(CustomErrorCode.MEMBER_NOT_EXIST));

        if (!bCryptPasswordEncoder.matches(dto.getPassword(), member.getPassword())) {
            throw new CustomException(CustomErrorCode.MEMBER_NOT_CORRECT);
        }

        return BaseResponse.of(
                true,
                "OK",
                "로그인 성공",
                List.of(
                        jwtUtils.generateToken(member)
                )
        );
    }

    public BaseResponse<?> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return BaseResponse.of(
                true,
                "OK",
                "로그아웃 성공",
                null
        );
    }

    // 프로필 정보 가져오기
    public MemberResponseDTO getMemberProfile(String memberId) {
        MemberEntity memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_EXIST));

        return MemberResponseDTO.builder()
                .idx(memberEntity.getIdx())
                .id(memberEntity.getId())
                .email(memberEntity.getEmail())
                .vocaBook(memberEntity.getVocaBook())
                .point(memberEntity.getPoint())
                .exp(memberEntity.getExp())
                .authority(memberEntity.getAuthority())
                .title(memberEntity.getTitle())
                .level(memberEntity.getLevel())
                .totalExp(memberEntity.getTotalExp())
                .build();
    }

    // 퀴즈를 맞힐 시 포인트 지급
    public BaseResponse<?> givePoint(String memberId) {
        final int POINTS_FOR_CORRECT_ANSWER = 100;

        Optional<MemberEntity> optionalUser = memberRepository.findById(memberId);
        if (optionalUser.isPresent()) {
            MemberEntity member = optionalUser.get();
            member.setPoint(member.getPoint() + POINTS_FOR_CORRECT_ANSWER);
            memberRepository.save(member);

            return BaseResponse.of(true, "OK", "포인트 지급 성공", null);
        } else {
            throw new CustomException(MEMBER_NOT_EXIST);
        }
    }

    // 경험치 지급
    public BaseResponse<?> giveExp(String memberId, boolean isCorrectAnswer) {
        final int EXP_FOR_CORRECT_ANSWER = 20;
        final int EXP_FOR_WRONG_ANSWER = 10;

        try {
            Optional<MemberEntity> optionalUser = memberRepository.findById(memberId);
            if (optionalUser.isPresent()) {
                MemberEntity member = optionalUser.get();
                long expToAdd = isCorrectAnswer ? EXP_FOR_CORRECT_ANSWER : EXP_FOR_WRONG_ANSWER;

                member.increaseExp(expToAdd);
                memberRepository.save(member);

                long currentExp = member.getTotalExp();
                String message = isCorrectAnswer
                        ? String.format("정답! 경험치 +%d. 현재 총 경험치: %d, 현재 레벨: %d", EXP_FOR_CORRECT_ANSWER, currentExp, member.getLevel())
                        : String.format("오답! 경험치 +%d. 현재 총 경험치: %d, 현재 레벨: %d", EXP_FOR_WRONG_ANSWER, currentExp, member.getLevel());

                return BaseResponse.of(true, "OK", message, null);
            } else {
                throw new CustomException(MEMBER_NOT_EXIST);
            }
        } catch (Exception e) {
            throw new CustomException(SERVER_ERROR);
        }
    }
}
