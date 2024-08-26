package Minari.cheongForDo.domain.quiz.entity;

import Minari.cheongForDo.domain.quiz.dto.QuestionRequestDTO;
import Minari.cheongForDo.domain.quiz.model.enums.QuestionDifficulty;
import Minari.cheongForDo.domain.term.entity.Term;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Table(name = "Question")
public class Question {

    // 질문 Idx
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qtIdx;

    // 질문 내용
    @Column(nullable = false)
    private String qtContents;

    // 질문에 대한 정답
    @Column(nullable = false)
    private Boolean qtAnswer;

    // 질문에 대한 해설
    @Column(nullable = false)
    private String qtCmt;

    // 질문 난이도
    @Column(nullable = false)
    private QuestionDifficulty qtDifficulty;

    @Column(nullable = true)
    private String qtTip;

    public void update(QuestionRequestDTO requestDTO) {
        this.qtContents = requestDTO.getQtContents();
        this.qtAnswer = requestDTO.getQtAnswer();
        this.qtCmt = requestDTO.getQtCmt();
        this.qtDifficulty = requestDTO.getQtDifficulty();
        this.qtTip = requestDTO.getQtTip();
    }

}
