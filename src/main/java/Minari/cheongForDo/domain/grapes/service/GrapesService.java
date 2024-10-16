package Minari.cheongForDo.domain.grapes.service;

import Minari.cheongForDo.domain.grape.entity.Grape;
import Minari.cheongForDo.domain.grape.repository.GrapeRepository;
import Minari.cheongForDo.domain.grapeSeed.entity.GrapeSeed;
import Minari.cheongForDo.domain.grapes.dto.GrapesAllLoadRes;
import Minari.cheongForDo.domain.grapes.dto.GrapesCommandReq;
import Minari.cheongForDo.domain.grapes.dto.GrapesLoadRes;
import Minari.cheongForDo.domain.grapes.dto.GrapesUpdateReq;
import Minari.cheongForDo.domain.grapes.entity.Grapes;
import Minari.cheongForDo.domain.grapes.enums.GrapesAgeGroup;
import Minari.cheongForDo.domain.grapes.enums.GrapesWork;
import Minari.cheongForDo.domain.grapes.repository.GrapesRepository;
import Minari.cheongForDo.domain.learn.entity.Learn;
import Minari.cheongForDo.domain.learn.repository.LearnRepository;
import Minari.cheongForDo.domain.like.entity.Like;
import Minari.cheongForDo.domain.like.repository.LikeRepository;
import Minari.cheongForDo.domain.member.authority.MemberAccountType;
import Minari.cheongForDo.domain.member.entity.MemberEntity;
import Minari.cheongForDo.global.auth.UserSessionHolder;
import Minari.cheongForDo.global.exception.CustomException;
import Minari.cheongForDo.global.response.Response;
import Minari.cheongForDo.global.response.ResponseData;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static Minari.cheongForDo.global.exception.CustomErrorCode.GRAPES_NOT_EXIST;
import static Minari.cheongForDo.global.exception.CustomErrorCode.GRAPE_NOT_EXIST;
import static Minari.cheongForDo.global.exception.CustomErrorCode.MEMBER_NOT_AUTHORITY;

@Service
@Transactional
@RequiredArgsConstructor
public class GrapesService {

    private final GrapesRepository grapesRepository;
    private final UserSessionHolder userSessionHolder;
    private final LikeRepository likeRepository;
    private final LearnRepository learnRepository;
    private final GrapeRepository grapeRepository;

    public ResponseData<List<GrapesAllLoadRes>> findAllGrapes() {

        MemberEntity curMember = userSessionHolder.current();

        List<Grapes> gpsList = grapesRepository.findAll();
        checkListLike(gpsList, curMember);

        return ResponseData.of(HttpStatus.OK, "포도송이 전체 조회 성공!", gpsList.stream().map(GrapesAllLoadRes::of).toList());
    }

    public ResponseData<List<GrapesAllLoadRes>> findByCategoryGrapes(GrapesAgeGroup age, GrapesWork work) {
        List<Grapes> gpsList;

        if (age == null && work == null) {
            gpsList = grapesRepository.findAll();
        } else if (age == null) {
            gpsList = grapesRepository.findByGpsWork(work);
        } else if (work == null) {
            gpsList = grapesRepository.findByGpsAgeGroup(age);
        } else {
            gpsList = grapesRepository.findByGpsAgeGroupAndGpsWork(age, work);
        }

        return ResponseData.of(HttpStatus.OK, "포도송이 카테고리별 조회 성공!", gpsList.stream().map(GrapesAllLoadRes::of).toList());
    }


    public ResponseData<GrapesLoadRes> findOneGrapes(Long gpsId) {

        MemberEntity curMember = userSessionHolder.current();

        Grapes getGrapes = getGrapes(gpsId);

        Optional<Like> like = likeRepository.findByMemberAndGrapes(curMember, getGrapes);
        Optional<Learn> learn = learnRepository.findByMemberAndGrapes(curMember, getGrapes);

        checkLike(like, getGrapes);
        checkLearn(learn, getGrapes);

        updateGrape(getGrapes, curMember);

        GrapesLoadRes grapesList = GrapesLoadRes.of(getGrapes);

        return ResponseData.of(HttpStatus.OK, "포도송이 단일 조회 성공!", grapesList);
    }

    public Response createGrapes(GrapesCommandReq commandReq) {

        MemberEntity writer = userSessionHolder.current();
        checkMemberAuthority(writer);

        List<Grape> getGrapeList = getGrapeList(commandReq.gpIdList());

        Integer getGpsTime = getGpsTime(getGrapeList);
        Integer getGpsExp = getGpsExp(getGrapeList);

        Grapes grapes = commandReq.toEntity(getGrapeList, getGpsTime, getGpsExp);

        grapesRepository.save(grapes);

        return Response.of(HttpStatus.OK, "포도송이 생성 성공!");
    }

    public Response updateGrapes(Long gpsId, GrapesUpdateReq updateReq) {

        MemberEntity writer = userSessionHolder.current();
        checkMemberAuthority(writer);

        Grapes getGrapes = getGrapes(gpsId);
        getGrapes.update(updateReq);

        return Response.of(HttpStatus.OK, "포도송이 업데이트 완료!");
    }

    public Response deleteGrapes(Long gpsId) {

        MemberEntity writer = userSessionHolder.current();
        checkMemberAuthority(writer);

        Grapes getGrapes = getGrapes(gpsId);

        Optional<Like> like = likeRepository.findByMemberAndGrapes(writer, getGrapes);
        like.ifPresent(likeRepository::delete);

        Optional<Learn> learn = learnRepository.findByMemberAndGrapes(writer, getGrapes);
        learn.ifPresent(learnRepository::delete);

        grapesRepository.delete(getGrapes);

        return Response.of(HttpStatus.OK, "포도송이 삭제 성공!");
    }



    private void checkMemberAuthority(MemberEntity writer) {
        if (writer.getAuthority() != MemberAccountType.ROLE_ADMIN) {
            throw new CustomException(MEMBER_NOT_AUTHORITY);
        }
    }


    private Grapes getGrapes(Long gpsId) {
        return grapesRepository.findById(gpsId).orElseThrow(
                () -> new CustomException(GRAPES_NOT_EXIST)
        );
    }

    private Grape getGrape(Long gpId) {
        return grapeRepository.findById(gpId).orElseThrow(
                () -> new CustomException(GRAPE_NOT_EXIST));
    }

    private void updateGrape(Grapes grapes, MemberEntity curMember) {
        List<Grape> gpList = grapes.getGpList();
        int gpCnt = 0;

        for (Grape grape : gpList) {
            Optional<Like> like = likeRepository.findByMemberAndGrape(curMember, grape);
            Optional<Learn> learn = learnRepository.findByMemberAndGrape(curMember, grape);

            checkLike(like, grape);
            checkLearn(learn, grape);

            if (learn.isPresent()) {
                gpCnt += 1;
            }

            updateGrapeSeed(grape, curMember);
        }
        grapes.updateGpCnt(gpCnt);
    }

    private void updateGrapeSeed(Grape grape, MemberEntity curMember) {
        List<GrapeSeed> gpseList = grape.getGpseList();
        int gpseCnt = 0;

        for (GrapeSeed grapeSeed : gpseList) {
            Optional<Learn> learn = learnRepository.findByMemberAndGrapeSeed(curMember, grapeSeed);

            if (learn.isPresent()) {
                gpseCnt += 1;
            }
        }
        grape.updateGpseCnt(gpseCnt);
    }

    private List<Grape> getGrapeList(List<Long> grapeIdList) {
        List<Grape> grapeList = new ArrayList<>();
        for (Long gpId : grapeIdList) {
            Grape getGrape = getGrape(gpId);
            grapeList.add(getGrape);
        }
        return grapeList;
    }


    private Integer getGpsTime(List<Grape> grapeList) {
        Integer getGpsTime = 0;
        for (Grape grape : grapeList) {
            getGpsTime += grape.getGpTime();
        }
        return getGpsTime;
    }

    private Integer getGpsExp(List<Grape> grapeList) {
        Integer getGpsExp = 0;
        for (Grape grape : grapeList) {
            getGpsExp += grape.getGpExp();
        }
        return getGpsExp;
    }


    private void checkLike(Optional<Like> like, Grapes getGrapes) {
        if (like.isEmpty()) {
            getGrapes.gpsLikeFalse();
        } else {
            getGrapes.gpsLikeTrue();
        }
    }

    private void checkLearn(Optional<Learn> learn, Grapes getGrapes) {
        if (learn.isEmpty()) {
            getGrapes.gpsTFFalse();
        } else {
            getGrapes.gpsTFTrue();
        }
    }

    private void checkLike(Optional<Like> like, Grape getGrape) {
        if (like.isEmpty()) {
            getGrape.gpLikeFalse();
        } else {
            getGrape.gpLikeTrue();
        }
    }

    private void checkLearn(Optional<Learn> learn, Grape getGrape) {
        if (learn.isEmpty()) {
            getGrape.gpTFFalse();
        } else {
            getGrape.gpTFTrue();
        }
    }

    private void checkListLike(List<Grapes> grapesList, MemberEntity curMember) {
        for (Grapes getGrapes : grapesList) {
            Optional<Like> like = likeRepository.findByMemberAndGrapes(curMember, getGrapes);
            checkLike(like, getGrapes);
        }
    }
}
