package Minari.cheongForDo.domain.user.repository;

import Minari.cheongForDo.domain.user.entity.User;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();
}
