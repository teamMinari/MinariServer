package Minari.cheongForDo.domain.grapes.controller;

import Minari.cheongForDo.domain.grapes.dto.GrapesAllLoadRes;
import Minari.cheongForDo.domain.grapes.dto.GrapesCommandReq;
import Minari.cheongForDo.domain.grapes.dto.GrapesLoadRes;
import Minari.cheongForDo.domain.grapes.dto.GrapesUpdateReq;
import Minari.cheongForDo.domain.grapes.service.GrapesService;
import Minari.cheongForDo.global.response.Response;
import Minari.cheongForDo.global.response.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gps")
@RequiredArgsConstructor
public class GrapesController {

    private final GrapesService grapesService;

    @GetMapping
    public ResponseData<List<GrapesAllLoadRes>> findAllGrapes() {
        return grapesService.findAllGrapes();
    }

    @GetMapping("{gpsId}")
    public ResponseData<GrapesLoadRes> findOneGrapes(@PathVariable Long gpsId) {
        return grapesService.findOneGrapes(gpsId);
    }

    @PostMapping
    public Response createGrapes(@RequestBody GrapesCommandReq commandReq) {
        return grapesService.createGrapes(commandReq);
    }

    @PatchMapping("{gpsId}")
    public Response updateGrapes(@PathVariable Long gpsId, @RequestBody GrapesUpdateReq updateReq) {
        return grapesService.updateGrapes(gpsId, updateReq);
    }

    @DeleteMapping("{gpsId}")
    public Response deleteGrapes(@PathVariable Long gpsId) {
        return grapesService.deleteGrapes(gpsId);
    }
}
