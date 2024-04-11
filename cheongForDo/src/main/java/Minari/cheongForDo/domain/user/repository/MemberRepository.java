package Minari.cheongForDo.domain.user.repository;

import Minari.cheongForDo.domain.member.entity.MemberEntity;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    MemberEntity save(MemberEntity member);
    Optional<MemberEntity> findById(Long id);
    Optional<java.lang.reflect.Member> findByName(String name);
    List<java.lang.reflect.Member> findAll();
}
