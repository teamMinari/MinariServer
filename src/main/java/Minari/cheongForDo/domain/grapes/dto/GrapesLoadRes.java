package Minari.cheongForDo.domain.grapes.dto;

import Minari.cheongForDo.domain.grapes.entity.Grapes;
import Minari.cheongForDo.domain.grapes.enums.GrapesAgeGroup;
import Minari.cheongForDo.domain.grapes.enums.GrapesWork;

import java.util.List;
import java.util.stream.Collectors;

public record GrapesLoadRes(
        String gpsName,
        String gpsContent,
        Integer gpsTime,
        Boolean gpsLike,
        String gpsImg,
        Integer gpCnt,
        Integer gpCntMax,
        GrapesAgeGroup gpsAgeGroup,
        GrapesWork gpsWork,
        List<GpList> gpList
) {
        public record GpList(
            String gpImg,
            Long gpId,
            String gpNm,
            Integer gpExp,
            Integer gpTm,
            Boolean gpLike,
            Integer gpseCnt,
            Integer gpseCntMax
        ) {
    }

    public static GrapesLoadRes of(Grapes grapes) {
            List<GpList> gpList = grapes.getGpList().stream().map(grape -> new GpList(
                    grape.getGpImg(),
                    grape.getGpId(),
                    grape.getGpName(),
                    grape.getGpExp(),
                    grape.getGpTime(),
                    grape.getGpLike(),
                    grape.getGpseCnt(),
                    grape.getGpseCntMax()
            )).collect(Collectors.toList());

            return new GrapesLoadRes(
                    grapes.getGpsName(),
                    grapes.getGpsContent(),
                    grapes.getGpsTime(),
                    grapes.getGpsLike(),
                    grapes.getGpsImg(),
                    grapes.getGpCnt(),
                    grapes.getGpCntMax(),
                    grapes.getGpsAgeGroup(),
                    grapes.getGpsWork(),
                    gpList
            );
    }
}
