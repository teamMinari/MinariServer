package Minari.cheongForDo.domain.quiz.dto;

import Minari.cheongForDo.domain.quiz.entity.Question;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class QuestionResponseDTO { // 용어 필요?? 이거 필요 없을수도??

    @NotBlank
    private String qtName;

    @NotBlank
    private String qtContents;

    @NotBlank
    private Boolean qtAnswer;

    @NotBlank
    private String qtCmt;

    public static QuestionResponseDTO of (Question question) {
        return QuestionResponseDTO.builder()
                .qtName(question.getQtName())
                .qtContents(question.getQtContents())
                .qtAnswer(question.getQtAnswer())
                .qtCmt(question.getQtCmt()).build();
    }

}
