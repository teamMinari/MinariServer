package Minari.cheongForDo.domain.grape.entity;

import Minari.cheongForDo.domain.grape.dto.GrapeUpdateReq;
import Minari.cheongForDo.domain.grapeSeed.entity.GrapeSeed;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
public class Grape {

    // 포도알 id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gpId;

    // 포도알 이름
    @Column(nullable = false)
    private String gpName;

    // 포도알 시간
    @Column(nullable = false)
    private Integer gpTime;

    // 포도씨 전체 개수
    @Column(nullable = false)
    private Integer gpseCntMax;

    // 학습한 포도씨 개수, 유저 변동
    @Column(nullable = false)
    private Integer gpseCnt;

    // 포도알 진행도, 유저 변동
    @Column(nullable = false)
    private Integer gpProgress;

    // 포도알 학습 여부, 유저 변동
    @Column(nullable = false)
    private Boolean gpTF;

    // 포도씨 좋아요 여부, 유저 변동
    @Column(nullable = false)
    private Boolean gpLike;

    // 포도알 경험치 양
    @Column(nullable = false)
    private Integer gpExp;

    // 포도알 사진
    @Column(nullable = false)
    private String gpImg;

    // 포도씨 리스트
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable
    private List<GrapeSeed> gpseList;

    public void update(GrapeUpdateReq updateReq) {
        this.gpName = updateReq.gpName();
        this.gpImg = updateReq.gpImg();
    }

    public void GpLikeFalse() {
        this.gpLike = false;
    }

    public void GpLikeTrue() {
        this.gpLike = true;
    }

    public void GpTFFalse() {
        this.gpTF = false;
    }

    public void GpTFTrue() {
        this.gpTF = true;
    }
}
