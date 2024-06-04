package Minari.cheongForDo.global.auth;

import Minari.cheongForDo.domain.member.authority.MemberAccountType;
import Minari.cheongForDo.domain.member.entity.MemberEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomUserDetails implements UserDetails {

    private MemberEntity member;

    public static CustomUserDetails of(MemberEntity member) {
        return new CustomUserDetails(member);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(MemberAccountType.convert(member.getAuthority()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
//class CustomUserDetails extends MemberEntity implements UserDetails {
//    private final MemberEntity MEMBER;
//
//    private Long idx;
//    private String id;
//    private String password;
//    private String name;
//    private String email;
//    private LocalDate createdDate;
//    private String vocaBook;
//    private Long point;
//    private Long exp;
//    private MemberAccountType authority;
//
//
//
//
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Collection<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(MemberAccountType.convert(MEMBER.getAuthority()));
//        return authorities;
//    }
//
//    @Override
//    public Long getIdx() {
//        return idx;
//    }
//
//    @Override
//    public String getId() {
//        return id;
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getName() {
//        return name;
//    }
//
//    @Override
//    public String getEmail() {
//        return email;
//    }
//
//    @Override
//    public LocalDate getCreatedDate() {
//        return createdDate;
//    }
//
//    @Override
//    public String getVocaBook() {
//        return vocaBook;
//    }
//
//    @Override
//    public Long getPoint() {
//        return point;
//    }
//
//    @Override
//    public Long getExp() {
//        return exp;
//    }
//
//    @Override
//    public MemberAccountType getAuthority() {
//        return authority;
//    }
//
//    @Override
//    public String getUsername() {
//        return this.email;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//
//    public static CustomUserDetails of(MemberEntity member) {
//        return new CustomUserDetails(
//                member,
//                member.getIdx(),
//                member.getId(),
//                member.getPassword(),
//                member.getName(),
//                member.getEmail(),
//                member.getCreatedDate(),
//                member.getVocaBook(),
//                member.getPoint(),
//                member.getExp(),
//                member.getAuthority()
//        );
//    }
//}