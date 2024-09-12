package Minari.cheongForDo.domain.like.controller;

import Minari.cheongForDo.domain.grape.dto.GrapeLikeLoadRes;
import Minari.cheongForDo.domain.grapeSeed.dto.GrapeSeedLikeLoadRes;
import Minari.cheongForDo.domain.grapes.dto.GrapesLikeLoadRes;
import Minari.cheongForDo.domain.like.enums.LikeCategory;
import Minari.cheongForDo.domain.like.service.LikeService;
import Minari.cheongForDo.domain.term.dto.TermLikeLoadRes;
import Minari.cheongForDo.global.response.Response;
import Minari.cheongForDo.global.response.ResponseData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "LIKE", description = "like API")
public class LikeController {
    private final LikeService likeService;

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "좋아요 등록", description = "카테고리(GRAPES(포도송이), GRAPE(포도알), GRAPESEED(포도씨), TERM(용어) 중 하나를 받고, id를 받아서 만들어준다. 만약에 이미 좋아요가 있으면 삭제하고, 없으면 추가한다.")
    public Response patch(@RequestParam LikeCategory category, @RequestParam Long id) {
        return likeService.toggle(category, id);
    }

    @GetMapping("/term")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "좋아요 용어 조회", description = "좋아요가 있는 모든 용어를 리스트로 준다.")
    public ResponseData<List<TermLikeLoadRes>> myTerm() {
        return likeService.myTerm();
    }

    @GetMapping("/gps")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "좋아요 포도송이 조회", description = "좋아요가 있는 모든 포도송이를 리스트로 준다.")
    public ResponseData<List<GrapesLikeLoadRes>> myGrapes() {
        return likeService.myGrapes();
    }

    @GetMapping("/gp")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "좋아요 포도알 조회", description = "좋아요가 있는 모든 포도알을 리스트로 준다.")
    public ResponseData<List<GrapeLikeLoadRes>> myGrape() {
        return likeService.myGrape();
    }

    @GetMapping("/gpse")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "좋아요 포도씨 조회", description = "좋아요가 있는 모든 포도씨를 리스트로 준다.")
    public ResponseData<List<GrapeSeedLikeLoadRes>> myGrapeSeed() {
        return likeService.myGrapeSeed();
    }

}
