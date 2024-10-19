package Minari.cheongForDo.domain.term.dto;

import Minari.cheongForDo.domain.term.entity.Term;
import Minari.cheongForDo.domain.term.model.enums.TermDifficulty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TermLoadRes {

    @NotBlank
    private Long termId;

    @NotBlank
    private String termNm;

    @NotBlank
    private String termExplain;

    @NotBlank
    private TermDifficulty termDifficulty;

    public static TermLoadRes of(Term term) {
        return TermLoadRes.builder()
                .termId(term.getTermId())
                .termNm(term.getTermNm())
                .termExplain(term.getTermExplain())
                .termDifficulty(term.getTermDifficulty())
                .build();
    }
}
