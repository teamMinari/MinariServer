package Minari.cheongForDo.domain.like.controller;

import Minari.cheongForDo.domain.like.enums.LikeCategory;
import Minari.cheongForDo.domain.like.service.LikeService;
import Minari.cheongForDo.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

//    @GetMapping("/my")
//    public ResponseData<List<Term>> getMy(){
//        return likeService.getMy();
//    }

    @PatchMapping
    public Response patch(@RequestParam LikeCategory category, @RequestParam Long id) {
        return likeService.toggle(category, id);
    }

}
