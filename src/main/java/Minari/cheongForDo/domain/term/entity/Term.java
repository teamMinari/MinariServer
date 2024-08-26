package Minari.cheongForDo.domain.term.entity;

import Minari.cheongForDo.domain.term.dto.TermRequestDTO;
import Minari.cheongForDo.domain.term.model.enums.TermDifficulty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
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
public class Term {

    // 용어 이름
    @Id
    private String termNm;

    // 용어 내용
    @Column(nullable = false)
    private String termExplain;

    // 용어 난이도
    @Column
    @Enumerated(EnumType.STRING)
    private TermDifficulty termDifficulty;

    public void update(TermRequestDTO termRequestDTO) {
        this.termNm = termRequestDTO.getTermNm();
        this.termExplain = termRequestDTO.getTermExplain();
        this.termDifficulty = termRequestDTO.getTermDifficulty();
    }

}
