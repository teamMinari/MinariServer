package Minari.cheongForDo.domain.member.presentation.controller;

import Minari.cheongForDo.domain.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import Minari.cheongForDo.domain.member.entity.MemberEntity;

@RestController
@RequestMapping("/api")
public class MemberController {
    private final MemberService MEMBER_SERVICE;

    @Autowired
    public MemberController(MemberService MEMBER_SERVICE) {
        this.MEMBER_SERVICE = MEMBER_SERVICE;
    }

    @PostMapping("/google-register")
    public MemberEntity googleRegister(@RequestBody MemberEntity member) {
        return MEMBER_SERVICE.registerWithGoogle(member);
    }

    @GetMapping("/user/{id}")
    public MemberEntity getUserById(@PathVariable Long id) {
        return MEMBER_SERVICE.findById(id);
    }
}