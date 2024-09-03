package Minari.cheongForDo.domain.grapes.dto;

import Minari.cheongForDo.domain.grapes.enums.GrapesCategory;

import java.util.List;

public record GrapesUpdateReq(
        String gpsName,
        String gpsContent,
        String gpsImg,
        List<GrapesCategory> gpTpList
) {
}
