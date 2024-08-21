package Minari.cheongForDo.domain.term.dto;

import Minari.cheongForDo.domain.term.model.enums.TermDifficulty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private TermDifficulty termDifficulty;

}
