package Minari.cheongForDo.domain.grapes.dto;

import Minari.cheongForDo.domain.grapes.entity.Grapes;
import Minari.cheongForDo.domain.grapes.enums.GrapesAgeGroup;
import Minari.cheongForDo.domain.grapes.enums.GrapesWork;

public record GrapesLikeLoadRes(
        Long gpsId,
        String gpsContent,
        String gpsImg,
        Boolean gpsLike,
        GrapesAgeGroup gpsAgeGroup,
        GrapesWork gpsWork
) {
    public static GrapesLikeLoadRes of(Grapes grapes) {
        return new GrapesLikeLoadRes(
                grapes.getGpsId(),
                grapes.getGpsContent(),
                grapes.getGpsImg(),
                true,
                grapes.getGpsAgeGroup(),
                grapes.getGpsWork()
        );
    }
}
