package Minari.cheongForDo.domain.grapes.dto;

import Minari.cheongForDo.domain.grapes.entity.Grapes;
import Minari.cheongForDo.domain.grapes.enums.GrapesAgeGroup;
import Minari.cheongForDo.domain.grapes.enums.GrapesWork;

public record GrapesAllLoadRes (
        Long gpsId,
        String gpsName,
        String gpsContent,
        Integer gpsTime,
        Boolean gpsLike,
        GrapesAgeGroup gpsAgeGroup,
        GrapesWork gpsWork
) {
    public static GrapesAllLoadRes of(Grapes grapes) {
        return new GrapesAllLoadRes(
                grapes.getGpsId(),
                grapes.getGpsName(),
                grapes.getGpsContent(),
                grapes.getGpsTime(),
                grapes.getGpsLike(),
                grapes.getGpsAgeGroup(),
                grapes.getGpsWork()
                );
    }
}
