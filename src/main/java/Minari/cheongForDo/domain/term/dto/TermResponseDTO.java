package Minari.cheongForDo.domain.term.dto;

import Minari.cheongForDo.domain.term.entity.Term;
import Minari.cheongForDo.domain.term.model.enums.TermDifficulty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
public class TermResponseDTO {

    @NotBlank
    private Long termId;

    @NotBlank
    private String termNm;

    @NotBlank
    private String termExplain;

    @NotBlank
    private TermDifficulty termDifficulty;

    public static TermResponseDTO of(Term term) {
        return TermResponseDTO.builder()
                .termId(term.getTermId())
                .termNm(term.getTermNm())
                .termExplain(term.getTermExplain())
                .termDifficulty(term.getTermDifficulty())
                .build();
    }
}
