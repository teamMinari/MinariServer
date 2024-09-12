package Minari.cheongForDo.domain.member.entity;


import Minari.cheongForDo.domain.grapes.entity.Grapes;
import Minari.cheongForDo.domain.member.authority.MemberAccountType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


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
    @Column(name = "vocaBook")
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

    // 출석체크 보상
    @Column(name = "checkLevel", nullable = false)
    private Long checkLevel = 1L;

    // 회원 포도송이 진행도 리스트
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable
    private List<Grapes> gpsList;

    @Column(name = "last_attendance_date")
    private LocalDateTime lastAttendanceDate;

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
            Long level,
            Long checkLevel,
            List<Grapes> gpsList
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
        this.checkLevel = checkLevel;
        this.gpsList = gpsList;
    }

    // 경험치 레벨 기준
    private static final long[] REQUIRED_EXP_PER_LEVEL = {0, 100, 300, 600, 1000, 1500, 2100, 2800, 3600, 4500, 5500};

    // 보상(칭호) 해금 기준(?)
    private static final long[] CHECK_LEVEL_EXP = {0, 70, 140, 210, 280, 350, 420, 490, 560, 630};

    public void increaseExp(long expToAdd) {
        increaseExp(expToAdd, false);
    }

    public void increaseExp(long expToAdd, boolean isAttendanceCheck) {
        this.exp += expToAdd; // 경험치가 실제로 추가되는지 확인
        if (isAttendanceCheck) {
            checkAttendanceLevelUp();  // 출석 체크 시 레벨업 체크
        } else {
            checkLevelUp();  // 일반 경험치 증가 시 레벨업 체크
        }
    }

    // 포인트 추가 메서드
    public void increasePoint(long pointToAdd) {
        this.point += pointToAdd; // 포인트 추가
    }

    // 유저 레벨 체크 메서드
    private void checkLevelUp() {
        while (this.level < REQUIRED_EXP_PER_LEVEL.length - 1 && this.exp >= REQUIRED_EXP_PER_LEVEL[this.level.intValue()]) {
            this.exp -= REQUIRED_EXP_PER_LEVEL[this.level.intValue()];
            this.level++;
        }
    }

    // 보상지급 레벨 체크 메서드
    private void checkAttendanceLevelUp() {
        while (this.checkLevel < CHECK_LEVEL_EXP.length - 1 && this.exp >= CHECK_LEVEL_EXP[this.checkLevel.intValue()]) {
            this.exp -= CHECK_LEVEL_EXP[this.checkLevel.intValue()];
            this.checkLevel++;
        }
    }

    // 유저의 전체 경험치
    public long getTotalExp() {
        long totalExp = 0;
        // 이전 레벨까지의 총 경험치 더하기
        for (int i = 0; i < this.level; i++) {
            totalExp += REQUIRED_EXP_PER_LEVEL[i];
        }
        // 현재 레벨에서 남아 있는 경험치 더하기
        totalExp += this.exp;
        return totalExp;
    }

}