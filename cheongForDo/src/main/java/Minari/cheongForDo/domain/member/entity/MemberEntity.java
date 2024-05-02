package Minari.cheongForDo.domain.member.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@Entity
@Table(name = "member")
@NoArgsConstructor
public class MemberEntity {


    // 회원 Id
    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

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
}
