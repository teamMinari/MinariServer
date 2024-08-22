package Minari.cheongForDo.domain.member.service;


import Minari.cheongForDo.domain.member.authority.MemberAccountType;
import Minari.cheongForDo.domain.member.entity.MemberEntity;
import Minari.cheongForDo.domain.member.presentation.dto.MemberExpResponseDTO;
import Minari.cheongForDo.domain.member.presentation.dto.MemberLoginDTO;
import Minari.cheongForDo.domain.member.presentation.dto.MemberResponseDTO;
import Minari.cheongForDo.domain.member.repository.MemberRepository;
import Minari.cheongForDo.domain.member.presentation.dto.MemberRegisterDTO;
import Minari.cheongForDo.global.auth.JwtInfo;
import Minari.cheongForDo.global.auth.JwtUtils;
import Minari.cheongForDo.global.auth.UserSessionHolder;
import Minari.cheongForDo.global.exception.CustomErrorCode;
import Minari.cheongForDo.global.exception.CustomException;
import Minari.cheongForDo.global.response.BaseResponse;
import Minari.cheongForDo.global.response.Response;
import Minari.cheongForDo.global.response.ResponseData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final UserSessionHolder userSessionHolder;

    // 회원가입
    public Response postRegister(MemberRegisterDTO dto) {

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
                        .point(0L)
                        .exp(0L)
                        .authority(MemberAccountType.ROLE_USER)
                        .title(null)
                        .level(1L)
                        .build()
        );

        return Response.of(
                HttpStatus.OK,
                "회원가입 성공!"
        );
    }

    // 로그인
    public ResponseData<JwtInfo> postLogin(MemberLoginDTO dto) {
        MemberEntity member = memberRepository.findById(dto.getId())
                .orElseThrow(() -> new CustomException(CustomErrorCode.MEMBER_NOT_EXIST));

        if (!bCryptPasswordEncoder.matches(dto.getPassword(), member.getPassword())) {
            throw new CustomException(CustomErrorCode.MEMBER_NOT_CORRECT);
        }

        JwtInfo jwtInfo = jwtUtils.generateToken(member);

        return ResponseData.of(
                HttpStatus.OK,
                "로그인 성공!",
                jwtInfo
        );
    }

    // 로그아웃
    public Response logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return Response.of(
                HttpStatus.OK,
                "로그아웃 성공!"
        );
    }

    // 프로필 정보 가져오기
    public ResponseData<MemberResponseDTO> getMemberProfile() {

        MemberEntity memberEntity = userSessionHolder.current();

        MemberResponseDTO memberResponseDTO = MemberResponseDTO.builder()
                .idx(memberEntity.getIdx())
                .id(memberEntity.getId())
                .email(memberEntity.getEmail())
                .point(memberEntity.getPoint())
                .exp(memberEntity.getExp())
                .authority(memberEntity.getAuthority())
                .title(memberEntity.getTitle())
                .level(memberEntity.getLevel())
                .totalExp(memberEntity.getTotalExp())
                .build();

        return ResponseData.of(HttpStatus.OK, "회원 프로필 조회 성공!", memberResponseDTO);
    }

    // 퀴즈를 맞힐 시 포인트 지급
    public Response givePoint(String memberId) {
        final int POINTS_FOR_CORRECT_ANSWER = 100;

        Optional<MemberEntity> optionalUser = memberRepository.findById(memberId);
        if (optionalUser.isPresent()) {
            MemberEntity member = optionalUser.get();
            member.setPoint(member.getPoint() + POINTS_FOR_CORRECT_ANSWER);
            memberRepository.save(member);

            return Response.of(HttpStatus.OK,"포인트 지급 성공!");
        } else {
            throw new CustomException(MEMBER_NOT_EXIST);
        }
    }

    // 경험치 지급 로직
    public ResponseData<MemberExpResponseDTO> giveExp(boolean isCorrectAnswer) {
        final int EXP_FOR_CORRECT_ANSWER = 20;
        final int EXP_FOR_WRONG_ANSWER = 10;

        try {
            MemberEntity member = userSessionHolder.current();

            long expToAdd = isCorrectAnswer ? EXP_FOR_CORRECT_ANSWER : EXP_FOR_WRONG_ANSWER;

            member.increaseExp(expToAdd);
            memberRepository.save(member);

            long currentExp = member.getTotalExp();
            String answerType = isCorrectAnswer ? "정답" : "오답";
            String message = String.format("%s! 경험치 +%d. 현재 총 경험치: %d, 현재 레벨: %d",
                    answerType, expToAdd, currentExp, member.getLevel());

            return ResponseData.of(HttpStatus.OK, message, MemberExpResponseDTO.of(member));
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(SERVER_ERROR);
        }
    }
}
