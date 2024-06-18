package Minari.cheongForDo.domain.member.entity;


import Minari.cheongForDo.domain.member.authority.MemberAccountType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@Entity
@Table(name = "member")
@NoArgsConstructor
public class MemberEntity extends BaseTimeEntity {

    // 회원 pk
    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    // 회원 아이디
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "vocaBook", nullable = true)
    private String vocaBook;

    @Column(name = "point", nullable = true)
    private Long point;

    @Column(name = "exp", nullable = true)
    private Long exp;

    @Column(name = "authority", nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberAccountType authority;

    @Builder
    public MemberEntity (
            String id,
            String password,
            String name,
            String email,
            MemberAccountType authority
    ) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.authority = authority;
    }
}
