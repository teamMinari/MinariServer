package Minari.cheongForDo.domain.term.dto;

import Minari.cheongForDo.domain.term.entity.Term;
import Minari.cheongForDo.domain.term.model.enums.TermDifficulty;

public record TermOneLoadLikeRes(
        Long termId,
        String termNm,
        String termExplain,
        TermDifficulty termDifficulty,
        Boolean termLike
) {
    public static TermOneLoadLikeRes of(Term term, Boolean termLike) {
        return new TermOneLoadLikeRes(
                term.getTermId(),
                term.getTermNm(),
                term.getTermExplain(),
                term.getTermDifficulty(),
                termLike
        );
    }
}
