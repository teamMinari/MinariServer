package Minari.cheongForDo.domain.member.presentation.controller;

import Minari.cheongForDo.domain.member.presentation.dto.*;
import Minari.cheongForDo.domain.member.service.MemberService;
import Minari.cheongForDo.global.auth.JwtInfo;
import Minari.cheongForDo.global.response.Response;
import Minari.cheongForDo.global.response.ResponseData;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Tag(name = "MEMBER", description = "member API")
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

    // 토큰 갱신
    @PostMapping("/refresh")
    public ResponseData<JwtInfo> refreshAccessToken(@RequestBody RefreshTokenRequestDTO dto) {
        return memberService.refreshAccessToken(dto);
    }

    // 프로필 확인
    @GetMapping("/profile")
    public @ResponseBody MemberResponseDTO getMember() {
        return memberService.getMemberProfile().data();
    }

    // 포인트 지급
    @PostMapping("/givePoint")
    public ResponseEntity<MemberPointResponseDTO> givePoint(@RequestBody MemberPointRequestDTO request) {
        MemberPointResponseDTO response = memberService.givePoint(request.getPointToAdd());
        return ResponseEntity.ok(response);
    }

    // 경험치 지급
    @PostMapping("/giveExp")
    public ResponseEntity<MemberExpResponseDTO> giveExp(@RequestBody MemberExpRequestDTO request) {
        MemberExpResponseDTO response = memberService.giveExp(request.getExpToAdd());
        return ResponseEntity.ok(response);
    }

    // 출석체크
    @PostMapping("/check")
    public ResponseEntity<MemberExpResponseDTO> checkAttendance(@RequestBody AttendanceRequestDTO request) {
        MemberExpResponseDTO response = memberService.checkAttendance(request.getExpToAdd());
        return ResponseEntity.ok(response);
    }

    // 랭킹
    @GetMapping("/rank")
    public ResponseEntity<List<RankingResponseDTO>> getMemberRank() {
        return ResponseEntity.ok(memberService.getRank());
    }

    // cheongfordo.kr/로 접속했을 경우 그냥 아무거나 뜨도록
    @GetMapping("/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("청소년을 위한 포켓 경제 서비스 청포도입니다.");
    }
}