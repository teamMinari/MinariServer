package Minari.cheongForDo.domain.grapeSeed.dto;

import Minari.cheongForDo.domain.grapeSeed.entity.GrapeSeed;
import Minari.cheongForDo.domain.term.entity.Term;

import java.util.List;

public record GrapeSeedCommandReq(
        String gpseName,
        String gpseContent,
        Integer gpseTime,
        Integer gpseExp,
        Long gpseQtId,
        String gpseUrl,
        List<Long> termIdList
) {
    public GrapeSeed toEntity(List<Term> getTermList) {
        return GrapeSeed.builder()
                .gpseName(gpseName)
                .gpseContent(gpseContent)
                .gpseTime(gpseTime)
                .gpseTF(false)
                .gpseLike(false)
                .gpseExp(gpseExp)
                .gpseQtId(gpseQtId)
                .gpseUrl(gpseUrl)
                .termNameList(getTermList)
                .build();
    }
}
