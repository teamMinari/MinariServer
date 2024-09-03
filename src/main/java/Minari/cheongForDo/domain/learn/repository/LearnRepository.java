package Minari.cheongForDo.domain.learn.repository;

import Minari.cheongForDo.domain.grape.entity.Grape;
import Minari.cheongForDo.domain.grapeSeed.entity.GrapeSeed;
import Minari.cheongForDo.domain.grapes.entity.Grapes;
import Minari.cheongForDo.domain.learn.entity.Learn;
import Minari.cheongForDo.domain.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LearnRepository extends JpaRepository<Learn, Long> {
    Optional<Learn> findByMemberAndGrapeSeed(MemberEntity member, GrapeSeed grapeSeed);
    Optional<Learn> findByMemberAndGrape(MemberEntity member, Grape grape);
    Optional<Learn> findByMemberAndGrapes(MemberEntity member, Grapes grapes);
}
