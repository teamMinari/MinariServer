package Minari.cheongForDo.domain.grapeSeed.controller;

import Minari.cheongForDo.domain.grapeSeed.dto.GrapeSeedCommandReq;
import Minari.cheongForDo.domain.grapeSeed.dto.GrapeSeedLoadRes;
import Minari.cheongForDo.domain.grapeSeed.dto.GrapeSeedUpdateReq;
import Minari.cheongForDo.domain.grapeSeed.service.GrapeSeedService;
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

@RestController
@RequestMapping("/gpse")
@RequiredArgsConstructor
public class GrapeSeedController {

    private final GrapeSeedService grapeSeedService;

    @GetMapping("{gpseId}")
    public ResponseData<GrapeSeedLoadRes> findGrapeSeed(@PathVariable Long gpseId) {
        return grapeSeedService.findGrapeSeed(gpseId);
    }

    @PostMapping
    public Response createGrapeSeed(@RequestBody GrapeSeedCommandReq commandReq) {
        return grapeSeedService.createGrapeSeed(commandReq);
    }

    @PatchMapping("{gpseId}")
    public Response updateGrapeSeed(@PathVariable Long gpseId, @RequestBody GrapeSeedUpdateReq updateReq) {
        return grapeSeedService.updateGrapeSeed(gpseId, updateReq);
    }

    @DeleteMapping("{gpseId}")
    public Response deleteGrapeSeed(@PathVariable Long gpseId) {
        return grapeSeedService.deleteGrapeSeed(gpseId);
    }

}
