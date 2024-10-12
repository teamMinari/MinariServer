package Minari.cheongForDo.domain.term.dto;

import Minari.cheongForDo.domain.term.entity.Term;
import Minari.cheongForDo.domain.term.model.enums.TermDifficulty;

public record TermOneLikeLoadRes(
        Long termId,
        String termNm,
        String termExplain,
        TermDifficulty termDifficulty,
        Boolean termLike
) {
    public static TermOneLikeLoadRes of(Term term, Boolean termLike) {
        return new TermOneLikeLoadRes(
                term.getTermId(),
                term.getTermNm(),
                term.getTermExplain(),
                term.getTermDifficulty(),
                termLike
        );
    }
}
