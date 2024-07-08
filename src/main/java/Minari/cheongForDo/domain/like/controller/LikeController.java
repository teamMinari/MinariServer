package Minari.cheongForDo.domain.like.controller;

import Minari.cheongForDo.domain.like.service.LikeService;
import Minari.cheongForDo.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @GetMapping("/my")
    public BaseResponse<?> getMy(){
        return likeService.getMy();
    }

    @PatchMapping("/toggle")
    public BaseResponse<?> patch(@RequestParam String word){
        return likeService.toggle(word);
    }
}
