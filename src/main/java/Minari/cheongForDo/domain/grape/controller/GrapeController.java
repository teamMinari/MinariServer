package Minari.cheongForDo.domain.grape.controller;

import Minari.cheongForDo.domain.grape.dto.GrapeCommandReq;
import Minari.cheongForDo.domain.grape.dto.GrapeLoadRes;
import Minari.cheongForDo.domain.grape.dto.GrapeUpdateReq;
import Minari.cheongForDo.domain.grape.service.GrapeService;
import Minari.cheongForDo.global.response.Response;
import Minari.cheongForDo.global.response.ResponseData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gp")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "GRAPE", description = "grape API")
public class GrapeController {

    private final GrapeService grapeService;

    @GetMapping("{gpId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "포도알 일일 조회")
    public ResponseData<List<GrapeLoadRes>> findGrape(@PathVariable Long gpId) {
        return grapeService.findGrape(gpId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "포도알 등록")
    public Response createGrape(@RequestBody GrapeCommandReq commandReq) {
        return grapeService.createGrape(commandReq);
    }

    @PatchMapping("{gpId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "포도알 업데이트")
    public Response updateGrape(@PathVariable Long gpId, @RequestBody GrapeUpdateReq updateReq) {
        return grapeService.updateGrape(gpId, updateReq);
    }

    @DeleteMapping("{gpId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "포도알 삭제")
    public Response deleteGrape(@PathVariable Long gpId) {
        return grapeService.deleteGrape(gpId);
    }
}
