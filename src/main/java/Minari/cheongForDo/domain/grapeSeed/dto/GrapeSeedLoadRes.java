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
            // List<GrapeSeedContentListRes> GrapeSeedContentListRes
) {
    public static GrapeSeedLoadRes of(GrapeSeed grapeSeed) {
        List<String> termNmList = grapeSeed.getTermNameList().stream()
                .map(Term::getTermNm)
                .toList();

        return new GrapeSeedLoadRes(
                grapeSeed.getGpseName(),
                grapeSeed.getGpseContent(),
                grapeSeed.getGpseTime(),
                grapeSeed.getGpseTF(), // 이거 learn 만들어서 확인하는 로직 짜야 함..
                grapeSeed.getGpseLike(),// 이거 like 만들어야 함
                grapeSeed.getGpseExp(),
                grapeSeed.getGpseQtId(),
                grapeSeed.getGpseUrl(),
                termNmList
        );
    }


//    public record GrapeSeedContentListRes(
//            String asdfasf,
//            String adfsf,
//            Integer sgd
//    ){
//    }
}


