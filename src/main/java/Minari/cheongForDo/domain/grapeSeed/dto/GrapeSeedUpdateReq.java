package Minari.cheongForDo.domain.grapeSeed.dto;

public record GrapeSeedUpdateReq(
        String gpseName,
        String gpseContent,
        Integer gpseTime,
        Integer gpseExp,
        Long gpseQtId,
        String gpseUrl
) {
}
