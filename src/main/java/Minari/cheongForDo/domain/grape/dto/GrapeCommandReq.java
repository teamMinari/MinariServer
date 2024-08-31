package Minari.cheongForDo.domain.grape.dto;

import Minari.cheongForDo.domain.grape.entity.Grape;
import Minari.cheongForDo.domain.grapeSeed.entity.GrapeSeed;

import java.util.List;

public record GrapeCommandReq(
        String gpName,
        String gpImg,
        List<Long> gpseIdList
) {
    public Grape toEntity(List<GrapeSeed> grapeSeedList, Integer gpTime, Integer gpExp) {
        return Grape.builder()
                .gpName(gpName)
                .gpTime(gpTime)
                .gpseCntMax(grapeSeedList.size())
                .gpseCnt(0)
                .gpProgress(0)
                .gpTF(false)
                .gpLike(false)
                .gpExp(gpExp)
                .gpImg(gpImg)
                .gpseList(grapeSeedList)
                .build();
    }
}
