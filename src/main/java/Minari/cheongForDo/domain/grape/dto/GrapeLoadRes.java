package Minari.cheongForDo.domain.grape.dto;

import Minari.cheongForDo.domain.grape.entity.Grape;
import Minari.cheongForDo.domain.grapeSeed.entity.GrapeSeed;

import java.util.List;
import java.util.stream.Collectors;

public record GrapeLoadRes(
        Long gpseId,
        String gpseNm,
        Integer gpseTm,
        Boolean gpseTF
) {
    public static GrapeLoadRes of(GrapeSeed grapeSeed) {
        return new GrapeLoadRes(
                grapeSeed.getGpseId(),
                grapeSeed.getGpseName(),
                grapeSeed.getGpseTime(),
                grapeSeed.getGpseTF()
        );
    }

    public static List<GrapeLoadRes> fromGrape(Grape grape) {
        return grape.getGpseList().stream()
                .map(GrapeLoadRes::of)
                .collect(Collectors.toList());
    }
}
