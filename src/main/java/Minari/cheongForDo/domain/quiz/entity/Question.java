package Minari.cheongForDo.domain.quiz.entity;

import Minari.cheongForDo.domain.quiz.dto.QuestionRequestDTO;
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

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Question")
public class Question { // 용어 idx? 이름? 용어를 받아와야 함??

    // 질문 Idx
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qtIdx;

    // 질문 이름
    @Column(nullable = false)
    private String qtName;

    // 질문 내용
    @Column(nullable = false)
    private String qtContents;

    // 질문에 대한 정답
    @Column(nullable = false)
    private Boolean qtAnswer;

    // 질문에 대한 해설
    @Column(nullable = false)
    private String qtCmt;

    @ManyToOne()
    @JoinColumn(name = "fk_term_id")
    private Term qtTerm;

    @Builder
    public Question(
        String qtName,
        String qtContents,
        Boolean qtAnswer,
        String qtCmt,
        Term qtTerm
    ) {
        this.qtName = qtName;
        this.qtContents = qtContents;
        this.qtAnswer = qtAnswer;
        this.qtCmt = qtCmt;
        this.qtTerm = qtTerm;
    }

    public void update(QuestionRequestDTO requestDTO) {
        this.qtName = requestDTO.getQtName();
        this.qtContents = requestDTO.getQtContents();
        this.qtAnswer = requestDTO.getQtAnswer();
        this.qtCmt = requestDTO.getQtCmt();
        this.qtTerm = requestDTO.getQtTerm();
    }

}
