package Minari.cheongForDo.domain.member.authority;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum MemberAccountType {

    ROLE_USER,
    ROLE_ADMIN,
    ROLE_VERIFIER; // 검증자 추가

    public static GrantedAuthority convert(MemberAccountType type) {
        switch (type) {
            case ROLE_USER :
                return new SimpleGrantedAuthority("ROLE_USER");
            case ROLE_ADMIN :
                return new SimpleGrantedAuthority("ROLE_ADMIN");
            case ROLE_VERIFIER :
                return new SimpleGrantedAuthority("ROLE_VERIFIER");
            default :
                throw new IllegalArgumentException("잘못된 권한 이름");
        }
    }

}
