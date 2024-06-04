package Minari.cheongForDo.domain.member.service;


import Minari.cheongForDo.domain.member.authority.MemberAccountType;
import Minari.cheongForDo.domain.member.entity.MemberEntity;
import Minari.cheongForDo.domain.member.presentation.dto.MemberLoginDTO;
import Minari.cheongForDo.domain.member.repository.MemberRepository;
import Minari.cheongForDo.domain.member.presentation.dto.MemberRegisterDTO;
import Minari.cheongForDo.global.auth.JwtInfo;
import Minari.cheongForDo.global.auth.JwtUtils;
import Minari.cheongForDo.global.exception.CustomException;
import Minari.cheongForDo.global.exception.ErrorCode;
import Minari.cheongForDo.global.response.BaseResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtils jwtUtils;

    public BaseResponse<?> postRegister(MemberRegisterDTO dto) {
        if (memberRepository.existsById(dto.getId())) {
            throw new CustomException(ErrorCode.MEMBER_ALREADY_EXIST);
        }

        memberRepository.save(
                MemberEntity.builder()
                        .id(dto.getId())
                        .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                        .name(dto.getName())
                        .email(dto.getEmail())
                        .authority(MemberAccountType.ROLE_USER)
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
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_EXIST));

        if (!bCryptPasswordEncoder.matches(dto.getPassword(), member.getPassword())) {
            throw new CustomException(ErrorCode.MEMBER_NOT_CORRECT);
        }

        JwtInfo jwtInfo = jwtUtils.generateToken(member);

        return BaseResponse.of(
                true,
                "OK",
                "로그인 성공",
                Collections.singletonList(jwtInfo)
        );
    }
}