package Minari.cheongForDo.domain.term.entity;

import Minari.cheongForDo.domain.term.dto.TermRequestDTO;
import Minari.cheongForDo.domain.term.model.enums.TermCategory;
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

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Term { // 추가로 들어가야 할 것 : 용어 유사어 termSiWd, 관련 사건, 관련 기사

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

    // 용어 카테고리
    @Column
    @Enumerated(EnumType.STRING)
    private TermCategory termCategory;


    // LikeEntity에 들어갈 것
    /*
        idx (ag, pk)
        @OneToMany term GET RequestParam
        memberId
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication(); 멤버 아이디 얻는 법 (service)
    */

    @Builder
    public Term(
            String termNm,
            String termExplain,
            TermDifficulty termDifficulty,
            TermCategory termCategory
    ) {
        this.termNm = termNm;
        this.termExplain = termExplain;
        this.termDifficulty = termDifficulty;
        this.termCategory = termCategory;
    }

    public void update(TermRequestDTO termRequestDTO) {
        this.termNm = termRequestDTO.getTermNm();
        this.termExplain = termRequestDTO.getTermExplain();
        this.termDifficulty = termRequestDTO.getTermDifficulty();
        this.termCategory = termRequestDTO.getTermCategory();
    }

}
