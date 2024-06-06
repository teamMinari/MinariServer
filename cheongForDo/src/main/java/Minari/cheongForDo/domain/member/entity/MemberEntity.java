package Minari.cheongForDo.domain.member.entity;


import Minari.cheongForDo.domain.member.authority.MemberAccountType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import static lombok.AccessLevel.PROTECTED;
import static lombok.AccessLevel.PRIVATE;


@Getter
@Setter
@Entity
@Table(name = "member")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
public class MemberEntity {

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

    @Column(name = "createdDate", nullable = false)
    private LocalDate createdDate;

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