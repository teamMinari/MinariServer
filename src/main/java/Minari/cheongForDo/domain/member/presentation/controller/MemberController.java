package Minari.cheongForDo.domain.member.presentation.controller;

import Minari.cheongForDo.domain.member.presentation.dto.MemberExpResponseDTO;
import Minari.cheongForDo.domain.member.presentation.dto.MemberLoginDTO;
import Minari.cheongForDo.domain.member.presentation.dto.MemberRegisterDTO;
import Minari.cheongForDo.domain.member.presentation.dto.MemberResponseDTO;
import Minari.cheongForDo.domain.member.service.MemberService;
import Minari.cheongForDo.global.auth.JwtInfo;
import Minari.cheongForDo.global.response.Response;
import Minari.cheongForDo.global.response.ResponseData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("/register")
    public Response postRegister(@Valid @RequestBody MemberRegisterDTO dto) {
        return memberService.postRegister(dto);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseData<JwtInfo> postLogin(@Valid @RequestBody MemberLoginDTO dto) {
        return memberService.postLogin(dto);
    }

    // 로그아웃
    @GetMapping("/logout")
    public Response logout(HttpServletRequest request, HttpServletResponse response) {
        return memberService.logout(request, response);
    }

    // 프로필 확인
    @GetMapping("/profile")
    public @ResponseBody MemberResponseDTO getMember() {
        return memberService.getMemberProfile().data();
    }

    // 포인트 지급
    @PostMapping("/{memberId}/givePoint")
    public Response givePoint(@PathVariable String memberId) {
        return memberService.givePoint(memberId);
    }

    // 경험치 지급
    @PostMapping("/{memberId}/giveExp")
    public ResponseData<MemberExpResponseDTO> giveExp(@RequestParam boolean isCorrectAnswer) {
        return memberService.giveExp(isCorrectAnswer);
    }

}
