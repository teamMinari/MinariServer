//package Minari.cheongForDo.domain.member.presentation.controller;
//
//
//import Minari.cheongForDo.domain.member.service.ProfileService;
//import jakarta.persistence.Id;
//import jakarta.websocket.server.PathParam;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.lang.reflect.Member;
//
//@RestController
//@RequestMapping("/api/profile")
//public class ProfileController {
//    private final ProfileService PROFILE_SERVICE;
//
//    @Autowired
//    public ProfileController(ProfileService PROFILE_SERVICE){
//        this.PROFILE_SERVICE = PROFILE_SERVICE;
//    }
//
//    @GetMapping("/{userId")
//    public Member getMemberProfile(@PathVariable Long memberId) {
//        return PROFILE_SERVICE.getUserProfile();
//    }
//
//    @PutMapping("/{userId}")
//    public Member updateUserProfile(@PathVariable Long userId, @RequestBody Member updateUser){
//        return PROFILE_SERVICE.updateUserProfile(userId, updateUser);
//    }
//}
