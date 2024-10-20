package Minari.cheongForDo.domain.grapes.dto;

import Minari.cheongForDo.domain.grape.entity.Grape;
import Minari.cheongForDo.domain.grapes.entity.Grapes;
import Minari.cheongForDo.domain.grapes.enums.GrapesAgeGroup;
import Minari.cheongForDo.domain.grapes.enums.GrapesWork;

import java.util.List;

public record GrapesCommandReq(
        String gpsName,
        String gpsContent,
        String gpsImg,
        GrapesAgeGroup gpsAgeGroup,
        GrapesWork gpsWork,
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
                .gpCnt(0)
                .gpProgress(0)
                .gpsExp(gpsExp)
                .gpsTF(false)
                .gpsLike(false)
                .gpsImg(gpsImg)
                .gpsAgeGroup(gpsAgeGroup)
                .gpsWork(gpsWork)
                .gpList(grapeList)
                .build();
    }
}
