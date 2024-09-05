package Minari.cheongForDo.domain.like.repository;

import Minari.cheongForDo.domain.grape.entity.Grape;
import Minari.cheongForDo.domain.grapeSeed.entity.GrapeSeed;
import Minari.cheongForDo.domain.grapes.entity.Grapes;
import Minari.cheongForDo.domain.like.entity.Like;
import Minari.cheongForDo.domain.member.entity.MemberEntity;
import Minari.cheongForDo.domain.term.entity.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByMemberAndTerm(MemberEntity member, Term term);
    Optional<Like> findByMemberAndGrapeSeed(MemberEntity member, GrapeSeed grapeSeed);
    Optional<Like> findByMemberAndGrape(MemberEntity curMember, Grape getGrape);
    Optional<Like> findByMemberAndGrapes(MemberEntity member, Grapes grapes);
    List<Like> findAllByMemberAndTermIsNotNull(MemberEntity member);
    List<Like> findAllByMemberAndGrapesIsNotNull(MemberEntity member);
    List<Like> findAllByMemberAndGrapeIsNotNull(MemberEntity member);
    List<Like> findAllByMemberAndGrapeSeedIsNotNull(MemberEntity member);
}
