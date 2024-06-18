package Minari.cheongForDo.domain.term.dto;

import Minari.cheongForDo.domain.term.model.enums.TermCategory;
import Minari.cheongForDo.domain.term.model.enums.TermDifficulty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TermRequestDTO {

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

}
