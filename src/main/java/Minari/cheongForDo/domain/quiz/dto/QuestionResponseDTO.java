package Minari.cheongForDo.domain.quiz.dto;

import Minari.cheongForDo.domain.quiz.entity.Question;
import Minari.cheongForDo.domain.quiz.model.enums.QuestionDifficulty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class QuestionResponseDTO { // qt 카테고리가 필요할 수도 있어요

    @NotBlank
    private String qtContents;

    @NotBlank
    private Boolean qtAnswer;

    @NotBlank
    private String qtCmt;

    @NotBlank
    private String qtTip;

    @NotBlank
    private QuestionDifficulty qtDifficulty;


    public static QuestionResponseDTO of (Question question) {
        return QuestionResponseDTO.builder()
                .qtContents(question.getQtContents())
                .qtAnswer(question.getQtAnswer())
                .qtCmt(question.getQtCmt())
                .qtTip(question.getQtTip())
                .qtDifficulty(question.getQtDifficulty()).build();
    }

}
