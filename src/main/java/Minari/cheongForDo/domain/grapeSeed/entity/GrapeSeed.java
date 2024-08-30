package Minari.cheongForDo.domain.grapeSeed.entity;

import Minari.cheongForDo.domain.grapeSeed.dto.GrapeSeedUpdateReq;
import Minari.cheongForDo.domain.term.entity.Term;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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
public class GrapeSeed { // learn, like 만들어보고 유저 변동을 갖고 있어야 하는 지 고민해보기

    // 포도씨 id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gpseId;

    // 포도씨 이름
    @Column(nullable = false)
    private String gpseName;

    // 포도씨 내용
    @Column(nullable = false)
    private String gpseContent;

    // 학습 예정 시간
    @Column(nullable = false)
    private Integer gpseTime;

    // 포도씨 학습 여부, 유저 변동
    @Column(nullable = false)
    private Boolean gpseTF;

    // 포도씨 좋아요 여부, 유저 변동
    @Column(nullable = false)
    @Getter
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
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn
    private List<Term> termNameList;

    public void update(GrapeSeedUpdateReq updateReq) {
        this.gpseName = updateReq.gpseName();
        this.gpseContent = updateReq.gpseContent();
        this.gpseTime = updateReq.gpseTime();
        this.gpseExp = updateReq.gpseExp();
        this.gpseQtId = updateReq.gpseQtId();
        this.gpseUrl = updateReq.gpseUrl();
    }

    public void GpseLikeFalse() {
        this.gpseLike = false;
    }

    public void GpseLikeTrue() {
        this.gpseLike = true;
    }

    public void GpseTFFalse() {
        this.gpseTF = false;
    }

    public void GpseTFTrue() {
        this.gpseTF = true;
    }
}
