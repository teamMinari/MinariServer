package Minari.cheongForDo.domain.user.service;

import Minari.cheongForDo.domain.user.entity.User;
import Minari.cheongForDo.domain.user.repository.UserRepository;

import java.util.List;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long join(User user){
        validateDupliacteUser(user);
        userRepository.save(user);
        return user.getId();
    }

    private void validateDupliacteUser(User user){
        userRepository.findByName(user.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

}
