package Minari.cheongForDo.domain.member.presentation.controller;

import Minari.cheongForDo.domain.member.presentation.dto.MemberLoginDTO;
import Minari.cheongForDo.domain.member.presentation.dto.MemberRegisterDTO;
import Minari.cheongForDo.domain.member.presentation.dto.MemberResponseDTO;
import Minari.cheongForDo.domain.member.service.MemberService;
import Minari.cheongForDo.global.response.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("/register")
    public BaseResponse<?> postRegister(@Valid @RequestBody MemberRegisterDTO dto) {
        return memberService.postRegister(dto);
    }

    // 로그인
    @PostMapping("/login")
    public BaseResponse<?> postLogin(@Valid @RequestBody MemberLoginDTO dto) {
        return memberService.postLogin(dto);
    }

    // 로그아웃
    @GetMapping("/logout")
    public BaseResponse<?> logout(HttpServletRequest request, HttpServletResponse response) {
        return memberService.logout(request, response);
    }

    // 프로필 확인
    @GetMapping("/{memberId}")
    public @ResponseBody MemberResponseDTO getMember(@PathVariable String memberId) {
        return memberService.getMemberProfile(memberId);
    }

    // 포인트 지급
    @PostMapping("/{memberId}/givePoint")
    public BaseResponse<?> givePoint(@PathVariable String memberId) {
        return memberService.givePoint(memberId);
    }

    // 경험치 지급
    @PostMapping("/{memberId}/giveExp")
    public BaseResponse<?> giveExp(@PathVariable String memberId, @RequestParam boolean isCorrectAnswer) {
        return memberService.giveExp(memberId, isCorrectAnswer);
    }

}
