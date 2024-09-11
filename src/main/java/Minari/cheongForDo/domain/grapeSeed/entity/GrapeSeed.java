package Minari.cheongForDo.domain.grapeSeed.entity;

import Minari.cheongForDo.domain.grapeSeed.dto.GrapeSeedUpdateReq;
import Minari.cheongForDo.domain.term.entity.Term;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class GrapeSeed {

    // 포도씨 id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gpseId;

    // 포도씨 이름
    @Column(nullable = false)
    private String gpseName;

    // 포도씨 내용
    @Column(nullable = false, length = 10000)
    private String gpseContent;

    // 학습 예정 시간
    @Column(nullable = false)
    private Integer gpseTime;

    // 포도씨 학습 여부, 유저 변동
    @Column(nullable = false)
    private Boolean gpseTF;

    // 포도씨 좋아요 여부, 유저 변동
    @Column(nullable = false)
    private Boolean gpseLike;

    // 포도씨 경험치 양
    @Column(nullable = false)
    private Integer gpseExp;

    // 포도씨 문제 id
    @Column
    private Long gpseQtId;

    // 스플라인 url
    @Column
    private String gpseUrl;

    // 포도씨 관련 용어 이름 리스트
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable
    private List<Term> termNameList;

    public void update(GrapeSeedUpdateReq updateReq) {
        this.gpseName = updateReq.gpseName();
        this.gpseContent = updateReq.gpseContent();
        this.gpseTime = updateReq.gpseTime();
        this.gpseExp = updateReq.gpseExp();
        this.gpseQtId = updateReq.gpseQtId();
        this.gpseUrl = updateReq.gpseUrl();
    }

    public void gpseLikeFalse() {
        this.gpseLike = false;
    }

    public void gpseLikeTrue() {
        this.gpseLike = true;
    }

    public void gpseTFFalse() {
        this.gpseTF = false;
    }

    public void gpseTFTrue() {
        this.gpseTF = true;
    }
}
