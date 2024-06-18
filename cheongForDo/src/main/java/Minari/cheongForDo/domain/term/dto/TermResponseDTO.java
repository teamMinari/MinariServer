package Minari.cheongForDo.domain.term.dto;

import Minari.cheongForDo.domain.term.entity.Term;
import Minari.cheongForDo.domain.term.model.enums.TermCategory;
import Minari.cheongForDo.domain.term.model.enums.TermDifficulty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TermResponseDTO {
    // 용어 이름
    @NotBlank
    private String termNm;

    @NotBlank
    private String termExplain;

    @NotBlank
    private TermDifficulty termDifficulty;

    @NotBlank
    private TermCategory termCategory;

    @NotBlank
    private Boolean termLike;

    public static TermResponseDTO of(Term term) {
        return TermResponseDTO.builder()
                .termNm(term.getTermNm())
                .termExplain(term.getTermExplain())
                .termDifficulty(term.getTermDifficulty())
                .termCategory(term.getTermCategory())
                .termLike(term.isTermLike())
                .build();
    }
}
