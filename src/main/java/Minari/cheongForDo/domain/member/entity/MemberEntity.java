package Minari.cheongForDo.domain.member.entity;


import Minari.cheongForDo.domain.member.authority.MemberAccountType;
import groovy.grape.Grape;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;


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

    // 비밀번호
    @Column(name = "password", nullable = false)
    private String password;

    // 이메일
    @Column(name = "email", nullable = false)
    private String email;

    // 단어장
    @Column(name = "vocaBook", nullable = true)
    private String vocaBook;

    // 포인트
    @Column(name = "point", nullable = false)
    private Long point = 0L;

    // 경험치
    @Column(name = "exp", nullable = false)
    private Long exp = 0L;

    // 권한
    @Column(name = "authority", nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberAccountType authority;

    // 칭호
    @Column(name = "title")
    private String title;

    // 레벨
    @Column(name = "level", nullable = false)
    private Long level = 1L;

//    @ManyToMany
//    @JoinTable(
//            name = "member_grape",
//            joinColumns = @JoinColumn(name = "member_id"),
//            inverseJoinColumns = @JoinColumn(name = "grape_id")
//    )
//    private Set<Grape> grapes;


    @Builder
    public MemberEntity (
            Long idx,
            String id,
            String password,
            String email,
            String vocaBook,
            Long point,
            Long exp,
            MemberAccountType authority,
            String title,
            Long level
    ) {
        this.idx = idx;
        this.id = id;
        this.password = password;
        this.email = email;
        this.vocaBook = vocaBook;
        this.point = point;
        this.exp = exp;
        this.authority = authority;
        this.title = title;
        this.level = level;
    }

    private static final long[] REQUIRED_EXP_PER_LEVEL = {0, 100, 300, 600, 1000, 1500, 2100, 2800, 3600, 4500, 5500};

    public void increaseExp(long expToAdd) {
        this.exp += expToAdd;
        checkLevelUp();
    }

    private void checkLevelUp() {
        while (this.level < REQUIRED_EXP_PER_LEVEL.length - 1 && this.exp >= REQUIRED_EXP_PER_LEVEL[this.level.intValue()]) {
            this.exp -= REQUIRED_EXP_PER_LEVEL[this.level.intValue()];
            this.level++;
        }
    }

    public long getTotalExp() {
        long totalExp = this.exp;
        for (int i = 0; i < this.level; i++) {
            totalExp += REQUIRED_EXP_PER_LEVEL[i];
        }
        return totalExp;
    }
}
