package Minari.cheongForDo.domain.like.repository;

import Minari.cheongForDo.domain.grapeSeed.entity.GrapeSeed;
import Minari.cheongForDo.domain.like.entity.Like;
import Minari.cheongForDo.domain.member.entity.MemberEntity;
import Minari.cheongForDo.domain.term.entity.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByMember(MemberEntity member);
    Optional<Like> findByMemberAndTerm(MemberEntity member, Term term);
    Optional<Like> findByMemberAndGrapeSeed(MemberEntity member, GrapeSeed grapeSeed);
}
