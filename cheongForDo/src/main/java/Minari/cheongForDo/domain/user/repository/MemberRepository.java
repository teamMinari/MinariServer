package Minari.cheongForDo.domain.user.repository;

import Minari.cheongForDo.domain.user.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<java.lang.reflect.Member> findByName(String name);
    List<java.lang.reflect.Member> findAll();
}
