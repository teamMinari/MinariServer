package Minari.cheongForDo.domain.grapeSeed.dto;

import Minari.cheongForDo.domain.grapeSeed.entity.GrapeSeed;

public record GrapeSeedCommandReq(
        String gpseName,
        String gpseContent,
        Integer gpseTime,
        Integer gpseExp,
        Long gpseQtId,
        String gpseUrl
) {
    public GrapeSeed toEntity() {
        return GrapeSeed.builder()
                .gpseName(gpseName)
                .gpseContent(gpseContent)
                .gpseTime(gpseTime)
                .gpseTF(false)
                .gpseExp(gpseExp)
                .gpseQtId(gpseQtId)
                .gpseUrl(gpseUrl)
                .build();
    }
}
