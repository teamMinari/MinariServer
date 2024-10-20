package Minari.cheongForDo.domain.grapes.dto;

import Minari.cheongForDo.domain.grapes.enums.GrapesAgeGroup;
import Minari.cheongForDo.domain.grapes.enums.GrapesWork;

public record GrapesUpdateReq(
        String gpsName,
        String gpsContent,
        String gpsImg,
        GrapesAgeGroup gpsAgeGroup,
        GrapesWork gpsWork
) {
}
