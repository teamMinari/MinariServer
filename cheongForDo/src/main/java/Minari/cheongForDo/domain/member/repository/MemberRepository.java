package Minari.cheongForDo.domain.member.repository;

import Minari.cheongForDo.domain.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    boolean existsById(String idx);
    Optional<MemberEntity> findBy(String idx);

    Optional<MemberEntity> findByEmail(String email);
}
