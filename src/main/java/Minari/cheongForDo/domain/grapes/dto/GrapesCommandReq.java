package Minari.cheongForDo.domain.grapes.dto;

import Minari.cheongForDo.domain.grape.entity.Grape;
import Minari.cheongForDo.domain.grapes.entity.Grapes;
import Minari.cheongForDo.domain.grapes.enums.GrapesCategory;

import java.util.List;

public record GrapesCommandReq(
        String gpsName,
        String gpsContent,
        String gpsImg,
        List<GrapesCategory> gpTpList,
        List<Long> gpIdList
) {
    public Grapes toEntity(List<Grape> grapeList,
                           Integer gpsTime,
                           Integer gpsExp)
    {
        return Grapes.builder()
                .gpsName(gpsName)
                .gpsContent(gpsContent)
                .gpsTime(gpsTime)
                .gpCntMax(grapeList.size())
                .gpCnt(0) // load 처리
                .gpProgress(0) // load 처리
                .gpsExp(gpsExp)
                .gpsTF(false)
                .gpsLike(false)
                .gpsImg(gpsImg)
                .gpTpList(gpTpList)
                .gpList(grapeList)
                .build();
    }
}
