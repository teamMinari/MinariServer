package Minari.cheongForDo.domain.term.dto;

import Minari.cheongForDo.domain.term.entity.Term;
import Minari.cheongForDo.domain.term.model.enums.TermDifficulty;


public record TermLikeLoadRes(
        Long termId,
        String termNm,
        String termExplain,
        TermDifficulty termDifficulty
) {
    public static TermLikeLoadRes of(Term term) {
        return new TermLikeLoadRes(
                term.getTermId(),
                term.getTermNm(),
                term.getTermExplain(),
                term.getTermDifficulty()
        );
    }
}
