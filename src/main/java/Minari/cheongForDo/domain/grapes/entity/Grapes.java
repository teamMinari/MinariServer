package Minari.cheongForDo.domain.grapes.entity;

import Minari.cheongForDo.domain.grape.entity.Grape;
import Minari.cheongForDo.domain.grapes.dto.GrapesUpdateReq;
import Minari.cheongForDo.domain.grapes.enums.GrapesAgeGroup;
import Minari.cheongForDo.domain.grapes.enums.GrapesWork;
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
public class Grapes {

    // 포도송이 id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gpsId;

    // 포도송이 이름
    @Column(nullable = false)
    private String gpsName;

    // 포도송이 내용
    @Column(nullable = false, length = 10000)
    private String gpsContent;

    // 포도송이 시간
    @Column(nullable = false)
    private Integer gpsTime;

    // 포도알 전체 개수
    @Column(nullable = false)
    private Integer gpCntMax;

    // 포도송이 경험치 양
    @Column(nullable = false)
    private Integer gpsExp;

    // 포도송이 진행도, 유저 변동
    @Column(nullable = false)
    private Integer gpProgress;

    // 학습한 포도알 개수, 유저 변동
    @Column(nullable = false)
    private Integer gpCnt;

    // 포도송이 학습 여부, 유저 변동
    @Column(nullable = false)
    private Boolean gpsTF;

    // 포도송이 좋아요 여부, 유저 변동
    @Column(nullable = false)
    private Boolean gpsLike;

    // 포도알 사진
    @Column(nullable = false)
    private String gpsImg;

    // 포도송이 연령대
    @Column
    private GrapesAgeGroup gpsAgeGroup;

    // 포도송이 관련 직업
    @Column
    private GrapesWork gpsWork;

    // 포도알 리스트
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable
    private List<Grape> gpList;


    public void update(GrapesUpdateReq updateReq) {
        this.gpsName = updateReq.gpsName();
        this.gpsContent = updateReq.gpsContent();
        this.gpsImg = updateReq.gpsImg();
        this.gpsAgeGroup = updateReq.gpsAgeGroup();
        this.gpsWork = updateReq.gpsWork();
    }

    public void gpsLikeFalse() {
        this.gpsLike = false;
    }

    public void gpsLikeTrue() {
        this.gpsLike = true;
    }

    public void gpsTFFalse() {
        this.gpsTF = false;
    }

    public void gpsTFTrue() {
        this.gpsTF = true;
    }


    public void updateGpCnt(Integer gpCnt) {
        this.gpCnt = gpCnt;
    }
}
