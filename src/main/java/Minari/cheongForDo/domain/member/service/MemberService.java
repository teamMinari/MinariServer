package Minari.cheongForDo.domain.member.service;


import Minari.cheongForDo.domain.member.authority.MemberAccountType;
import Minari.cheongForDo.domain.member.entity.MemberEntity;
import Minari.cheongForDo.domain.member.presentation.dto.*;
import Minari.cheongForDo.domain.member.repository.MemberRepository;
import Minari.cheongForDo.domain.oauth.dto.OAuthLoginRequestDTO;
import Minari.cheongForDo.global.auth.JwtInfo;
import Minari.cheongForDo.global.auth.JwtUtils;
import Minari.cheongForDo.global.auth.UserSessionHolder;
import Minari.cheongForDo.global.exception.CustomErrorCode;
import Minari.cheongForDo.global.exception.CustomException;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

        if (memberRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new CustomException(CustomErrorCode.EMAIL_ALREADY_EXIST);
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
                        .checkLevel(1L)
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

    // Google 로그인 처리
    public ResponseData<JwtInfo> postOAuth2Login(OAuthLoginRequestDTO dto) {
        // Google에서 제공한 이메일로 사용자 확인
        MemberEntity member = memberRepository.findByEmail(dto.getEmail())
                .orElseGet(() -> {
                    // 새로운 사용자 생성
                    MemberEntity newMember = MemberEntity.builder()
                            .id(dto.getId()) // 사용자 입력 ID 설정
                            .email(dto.getEmail())
                            .point(0L)
                            .exp(0L)
                            .authority(MemberAccountType.ROLE_USER)
                            .title(null)
                            .level(1L)
                            .checkLevel(1L)
                            .build();
                    return memberRepository.save(newMember);
                });

        // JWT 토큰 생성
        JwtInfo jwtInfo = jwtUtils.generateToken(member);
        return ResponseData.of(HttpStatus.OK, "Google 로그인 성공!", jwtInfo);
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


    public ResponseData<JwtInfo> refreshAccessToken(RefreshTokenRequestDTO dto) {
        String refreshToken = dto.getRefreshToken();

        // 리프레시 토큰이 유효한지 확인
        if (jwtUtils.validateToken(refreshToken)) {
            String userEmail = jwtUtils.getUserEmailFromToken(refreshToken);

            MemberEntity member = memberRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new CustomException(CustomErrorCode.MEMBER_NOT_EXIST));

            // 새로운 액세스 토큰 생성
            JwtInfo newJwtInfo = jwtUtils.generateToken(member);

            return ResponseData.of(HttpStatus.OK, "새로운 액세스 토큰 발급 성공", newJwtInfo);
        } else {
            throw new CustomException(CustomErrorCode.INVALID_REFRESH_TOKEN);
        }
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
                .checkLevel(memberEntity.getCheckLevel()) // 보상 지급 단계
                .totalExp(memberEntity.getTotalExp())
                .build();

        return ResponseData.of(HttpStatus.OK, "회원 프로필 조회 성공!", memberResponseDTO);
    }

    // 포인트 지급 로직
    public MemberPointResponseDTO givePoint(long pointToAdd) {
        MemberEntity member = userSessionHolder.current();
        member.increasePoint(pointToAdd);  // 포인트 추가
        memberRepository.save(member);
        return MemberPointResponseDTO.of(member);  // 현재 포인트 정보 반환
    }

    // 경험치 지급 로직
    public MemberExpResponseDTO giveExp(long expToAdd) {
        MemberEntity member = userSessionHolder.current();
        member.increaseExp(expToAdd); // 현재 경험치와 총 경험치 증가
        memberRepository.save(member);
        return MemberExpResponseDTO.of(member); // 총 경험치 포함
    }

    // 출석 체크 로직
    public MemberExpResponseDTO checkAttendance(long expToAdd) {
        MemberEntity member = userSessionHolder.current();
        member.increaseExp(expToAdd); // 지정된 경험치만 추가
        member.setLastAttendanceDate(LocalDateTime.now()); // 마지막 출석일 업데이트
        memberRepository.save(member); // 변경된 정보를 저장

        return MemberExpResponseDTO.of(member); // 경험치 정보 반환
    }

    // 랭킹(경험치를 기준으로 선정)
    public List<RankingResponseDTO> getRank() {
        List<MemberEntity> memberRank = memberRepository.findAll();
        // 어드민, 검즘자는 제외
        memberRank = memberRank.stream()
                .filter(member ->
                        !member.getAuthority().equals(MemberAccountType.ROLE_ADMIN) &&
                                !member.getAuthority().equals(MemberAccountType.ROLE_VERIFIER))
                .collect(Collectors.toList());

        memberRank.sort((m1, m2) -> Long.compare(m2.getTotalExp(), m1.getTotalExp()));
        return memberRank.stream()
                .map(RankingResponseDTO::of)
                .collect(Collectors.toList());
    }
}