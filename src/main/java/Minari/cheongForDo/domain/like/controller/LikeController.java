package Minari.cheongForDo.domain.like.controller;

import Minari.cheongForDo.domain.grape.dto.GrapeLikeLoadRes;
import Minari.cheongForDo.domain.grapeSeed.dto.GrapeSeedLikeLoadRes;
import Minari.cheongForDo.domain.grapes.dto.GrapesLikeLoadRes;
import Minari.cheongForDo.domain.like.enums.LikeCategory;
import Minari.cheongForDo.domain.like.service.LikeService;
import Minari.cheongForDo.domain.term.dto.TermLikeLoadRes;
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

    @PatchMapping
    public Response patch(@RequestParam LikeCategory category, @RequestParam Long id) {
        return likeService.toggle(category, id);
    }

    @GetMapping("/term")
    public ResponseData<List<TermLikeLoadRes>> myTerm() {
        return likeService.myTerm();
    }

    @GetMapping("/gps")
    public ResponseData<List<GrapesLikeLoadRes>> myGrapes() {
        return likeService.myGrapes();
    }

    @GetMapping("/gp")
    public ResponseData<List<GrapeLikeLoadRes>> myGrape() {
        return likeService.myGrape();
    }

    @GetMapping("/gpse")
    public ResponseData<List<GrapeSeedLikeLoadRes>> myGrapeSeed() {
        return likeService.myGrapeSeed();
    }

}
