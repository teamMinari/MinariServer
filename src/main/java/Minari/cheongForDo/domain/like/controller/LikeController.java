package Minari.cheongForDo.domain.like.controller;

import Minari.cheongForDo.domain.like.service.LikeService;
import Minari.cheongForDo.domain.term.entity.Term;
import Minari.cheongForDo.global.response.Response;
import Minari.cheongForDo.global.response.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @GetMapping("/my")
    public ResponseData<List<Term>> getMy(){
        return likeService.getMy();
    }

    @PatchMapping("/toggle")
    public Response patch(@RequestParam String word){
        return likeService.toggle(word);
    }
}
