package Minari.cheongForDo.domain.grapeSeed.dto;

import Minari.cheongForDo.domain.grapeSeed.entity.GrapeSeed;
import Minari.cheongForDo.domain.term.entity.Term;

import java.util.List;

public record GrapeSeedLoadRes(
            String gpseName,
            String gpseContent,
            Integer gpseTime,
            Boolean gpseTF,
            Boolean gpseLike,
            Integer gpseExp,
            Long gpseQtId,
            String gpseUrl,
            List<String> termNmList
) {
    public static GrapeSeedLoadRes of(GrapeSeed grapeSeed) {
        List<String> termNmList = grapeSeed.getTermNameList().stream()
                .map(Term::getTermNm)
                .toList();

        return new GrapeSeedLoadRes(
                grapeSeed.getGpseName(),
                grapeSeed.getGpseContent(),
                grapeSeed.getGpseTime(),
                grapeSeed.getGpseTF(),
                grapeSeed.getGpseLike(),
                grapeSeed.getGpseExp(),
                grapeSeed.getGpseQtId(),
                grapeSeed.getGpseUrl(),
                termNmList
        );
    }



}


