package Minari.cheongForDo.domain.like.entity;

import Minari.cheongForDo.domain.grape.entity.Grape;
import Minari.cheongForDo.domain.grapeSeed.entity.GrapeSeed;
import Minari.cheongForDo.domain.like.enums.LikeCategory;
import Minari.cheongForDo.domain.member.entity.MemberEntity;
import Minari.cheongForDo.domain.term.entity.Term;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity(name = "tbl_like")
@Getter
@NoArgsConstructor
@SuperBuilder
public class Like { // 포도송이, 포도알 연결 필요함

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeIdx;

    @ManyToOne()
    @JoinColumn(name = "fk_member_id")
    private MemberEntity member;

    @Enumerated(EnumType.STRING)
    private LikeCategory likeCategory;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_term_id")
    private Term term;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_grapeSeed_id")
    private GrapeSeed grapeSeed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_grape_id")
    private Grape grape;

}
