package Minari.cheongForDo.domain.quiz.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class QuestionRequestDTO { // 용어?

    @NotBlank
    private String qtName;

    @NotBlank
    private String qtContents;

    @NotBlank
    private Boolean qtAnswer;

    @NotBlank
    private String qtCmt;

}
