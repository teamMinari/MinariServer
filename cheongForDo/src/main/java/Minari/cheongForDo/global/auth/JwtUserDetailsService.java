package Minari.cheongForDo.global.auth;

import Minari.cheongForDo.domain.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final MemberRepository MEMBER_REPOSITORY;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new JwtUserDetails(
                MEMBER_REPOSITORY.findByEmail(email).get()
        );
    }
}
