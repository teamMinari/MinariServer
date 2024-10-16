package Minari.cheongForDo.domain.grapes.controller;

import Minari.cheongForDo.domain.grapes.dto.GrapesAllLoadRes;
import Minari.cheongForDo.domain.grapes.dto.GrapesCommandReq;
import Minari.cheongForDo.domain.grapes.dto.GrapesLoadRes;
import Minari.cheongForDo.domain.grapes.dto.GrapesUpdateReq;
import Minari.cheongForDo.domain.grapes.enums.GrapesAgeGroup;
import Minari.cheongForDo.domain.grapes.enums.GrapesWork;
import Minari.cheongForDo.domain.grapes.service.GrapesService;
import Minari.cheongForDo.global.response.Response;
import Minari.cheongForDo.global.response.ResponseData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gps")
@RequiredArgsConstructor
@Tag(name = "GRAPES", description = "grapes API")
public class GrapesController {

    private final GrapesService grapesService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "포도송이 전체 조회")
    public ResponseData<List<GrapesAllLoadRes>> findAllGrapes() {
        return grapesService.findAllGrapes();
    }

    @GetMapping("/category")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "포도송이 카테고리별 조회", description = "만약 없다면 아무 값도 안보내면 됩니다.")
    public ResponseData<List<GrapesAllLoadRes>> findByCategoryGrapes(GrapesAgeGroup age, GrapesWork work) {
        return grapesService.findByCategoryGrapes(age, work);
    }

    @GetMapping("{gpsId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "포도송이 일일 조회")
    public ResponseData<GrapesLoadRes> findOneGrapes(@PathVariable Long gpsId) {
        return grapesService.findOneGrapes(gpsId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "포도송이 등록")
    public Response createGrapes(@RequestBody GrapesCommandReq commandReq) {
        return grapesService.createGrapes(commandReq);
    }

    @PatchMapping("{gpsId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "포도송이 업데이트")
    public Response updateGrapes(@PathVariable Long gpsId, @RequestBody GrapesUpdateReq updateReq) {
        return grapesService.updateGrapes(gpsId, updateReq);
    }

    @DeleteMapping("{gpsId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "포도송이 삭제")
    public Response deleteGrapes(@PathVariable Long gpsId) {
        return grapesService.deleteGrapes(gpsId);
    }
}
