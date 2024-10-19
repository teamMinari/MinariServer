package Minari.cheongForDo.domain.term.entity;

import Minari.cheongForDo.domain.term.dto.TermCommandReq;
import Minari.cheongForDo.domain.term.model.enums.TermDifficulty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Term {

    // 용어 id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long termId;

    // 용어 이름
    @Column(nullable = false, unique = true)
    private String termNm;

    // 용어 내용
    @Column(nullable = false, length = 10000)
    private String termExplain;

    // 용어 난이도
    @Column
    @Enumerated(EnumType.STRING)
    private TermDifficulty termDifficulty;

    public void update(TermCommandReq termRequestDTO) {
        this.termNm = termRequestDTO.getTermNm();
        this.termExplain = termRequestDTO.getTermExplain();
        this.termDifficulty = termRequestDTO.getTermDifficulty();
    }

}
