package Minari.cheongForDo.domain.grapes.dto;

import Minari.cheongForDo.domain.grapes.entity.Grapes;
import Minari.cheongForDo.domain.grapes.enums.GrapesCategory;

import java.util.List;

public record GrapesLikeLoadRes(
        Long gpsId,
        String gpsContent,
        String gpsImg,
        Boolean gpsLike,
        List<GrapesCategory> gpsTpList
) {
    public static GrapesLikeLoadRes of(Grapes grapes) {
        return new GrapesLikeLoadRes(
                grapes.getGpsId(),
                grapes.getGpsContent(),
                grapes.getGpsImg(),
                true,
                grapes.getGpTpList()
        );
    }
}
