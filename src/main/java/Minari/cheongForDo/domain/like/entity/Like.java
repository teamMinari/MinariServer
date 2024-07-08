package Minari.cheongForDo.domain.like.entity;

import Minari.cheongForDo.domain.member.entity.MemberEntity;
import Minari.cheongForDo.domain.term.entity.Term;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "tbl_like")
@Getter
@NoArgsConstructor
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeIdx;

    @ManyToOne()
    @JoinColumn(name = "fk_member_id")
    private MemberEntity member;

    @ManyToOne()
    @JoinColumn(name = "fk_term_id")
    private Term term;

    @Builder
    public Like(Long likeIdx, MemberEntity member, Term term) {
        this.likeIdx = likeIdx;
        this.member = member;
        this.term = term;
    }

}
