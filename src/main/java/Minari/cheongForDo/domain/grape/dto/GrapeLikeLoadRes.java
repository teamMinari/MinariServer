package Minari.cheongForDo.domain.grape.dto;

import Minari.cheongForDo.domain.grape.entity.Grape;

public record GrapeLikeLoadRes(
        Long gpId,
        String gpName,
        String gpImg,
        Boolean gpLike
) {
    public static GrapeLikeLoadRes of(Grape grape) {
        return new GrapeLikeLoadRes(
                grape.getGpId(),
                grape.getGpName(),
                grape.getGpImg(),
                true
        );
    }
}
