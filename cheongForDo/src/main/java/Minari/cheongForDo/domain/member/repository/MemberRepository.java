package Minari.cheongForDo.domain.member.repository;

import Minari.cheongForDo.domain.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findById(String id);
    boolean existsById(String id);
    Optional<MemberEntity> findByEmail(String email);
}

