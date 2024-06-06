package Minari.cheongForDo.global.auth;

import Minari.cheongForDo.domain.member.entity.MemberEntity;
import Minari.cheongForDo.domain.member.repository.MemberRepository;
import Minari.cheongForDo.global.exception.CustomException;
import Minari.cheongForDo.global.exception.CustomErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@Primary
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository MEMBER_REPOSITORY;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MemberEntity member = MEMBER_REPOSITORY.findByEmail(email)
                .orElseThrow(() -> new CustomException(CustomErrorCode.MEMBER_NOT_FOUND));
        return createUserDetails(member);
    }

    private UserDetails createUserDetails(MemberEntity member) {
        return CustomUserDetails.of(member);
    }
}