package Minari.cheongForDo.domain.grapes.dto;

import Minari.cheongForDo.domain.grapes.entity.Grapes;
import Minari.cheongForDo.domain.grapes.enums.GrapesCategory;

import java.util.List;

public record GrapesAllLoadRes (
        Long gpsId,
        String gpsName,
        String gpsContent,
        Integer gpsTime,
        Boolean gpsLike,
        List<GrapesCategory> gpTpList
) {
    public static GrapesAllLoadRes of(Grapes grapes) {
        return new GrapesAllLoadRes(
                grapes.getGpsId(),
                grapes.getGpsName(),
                grapes.getGpsContent(),
                grapes.getGpsTime(),
                grapes.getGpsLike(),
                grapes.getGpTpList()
                );
    }
}
