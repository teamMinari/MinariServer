package Minari.cheongForDo.domain.grapeSeed.dto;

import Minari.cheongForDo.domain.grapeSeed.entity.GrapeSeed;

public record GrapeSeedLikeLoadRes(
        Long gpseId,
        String gpseName,
        Integer gpseTime,
        Boolean gpseLike
) {
    public static GrapeSeedLikeLoadRes of(GrapeSeed grapeSeed) {
        return new GrapeSeedLikeLoadRes(
                grapeSeed.getGpseId(),
                grapeSeed.getGpseName(),
                grapeSeed.getGpseTime(),
                true
        );
    }
}
