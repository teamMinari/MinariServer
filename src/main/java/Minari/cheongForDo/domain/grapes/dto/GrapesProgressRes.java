package Minari.cheongForDo.domain.grapes.dto;

import Minari.cheongForDo.domain.grapes.entity.Grapes;

public record GrapesProgressRes(
        Long gpsId,
        String gpsName,
        Integer gpsProgress
) {
    public static GrapesProgressRes of(Grapes grapes) {
        return new GrapesProgressRes(
                grapes.getGpsId(),
                grapes.getGpsName(),
                grapes.getGpProgress()
        );
    }
}
