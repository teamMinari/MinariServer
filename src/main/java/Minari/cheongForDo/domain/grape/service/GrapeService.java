package Minari.cheongForDo.domain.grape.service;

import Minari.cheongForDo.domain.grape.dto.GrapeCommandReq;
import Minari.cheongForDo.domain.grape.dto.GrapeLoadRes;
import Minari.cheongForDo.domain.grape.dto.GrapeUpdateReq;
import Minari.cheongForDo.domain.grape.entity.Grape;
import Minari.cheongForDo.domain.grape.repository.GrapeRepository;
import Minari.cheongForDo.domain.grapeSeed.entity.GrapeSeed;
import Minari.cheongForDo.domain.grapeSeed.repository.GrapeSeedRepository;
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

import static Minari.cheongForDo.global.exception.CustomErrorCode.GRAPESEED_NOT_EXIST;
import static Minari.cheongForDo.global.exception.CustomErrorCode.GRAPE_NOT_EXIST;
import static Minari.cheongForDo.global.exception.CustomErrorCode.MEMBER_NOT_AUTHORITY;

@Service
@Transactional
@RequiredArgsConstructor
public class GrapeService {

    private final GrapeRepository grapeRepository;
    private final UserSessionHolder userSessionHolder;
    private final LikeRepository likeRepository;
    private final LearnRepository learnRepository;
    private final GrapeSeedRepository grapeSeedRepository;

    public ResponseData<List<GrapeLoadRes>> findGrape(Long gpId) {

        MemberEntity curMember = userSessionHolder.current();

        Grape getGrape = getGrape(gpId);

        Optional<Like> like = likeRepository.findByMemberAndGrape(curMember, getGrape);
        Optional<Learn> learn = learnRepository.findByMemberAndGrape(curMember, getGrape);

        checkLike(like, getGrape);
        checkLearn(learn, getGrape);

        List<GrapeLoadRes> grapeSeedList = GrapeLoadRes.fromGrape(getGrape);

        return ResponseData.of(HttpStatus.OK, "포도씨 목록 조회 성공", grapeSeedList);
    }

    public Response createGrape(GrapeCommandReq commandReq) {

        MemberEntity writer = userSessionHolder.current();
        checkMemberAuthority(writer);

        List<GrapeSeed> getGpseList = getGpseList(commandReq.gpseIdList());

        Integer getGpTime = getGpTime(getGpseList);
        Integer getGpExp = getGpExp(getGpseList);

        Grape grape = commandReq.toEntity(getGpseList, getGpTime, getGpExp);

        grapeRepository.save(grape);

        return Response.of(HttpStatus.OK, "포도알 생성 성공!");
    }

    public Response updateGrape(Long gpId, GrapeUpdateReq updateReq) {

        MemberEntity writer = userSessionHolder.current();
        checkMemberAuthority(writer);

        Grape getGrape = getGrape(gpId);
        getGrape.update(updateReq);

        return Response.of(HttpStatus.OK, "포도알 업데이트 완료!");
    }

    public Response deleteGrape(Long gpId) {

        MemberEntity writer = userSessionHolder.current();
        checkMemberAuthority(writer);

        Grape getGrape = getGrape(gpId);

        Optional<Like> like = likeRepository.findByMemberAndGrape(writer, getGrape);
        like.ifPresent(likeRepository::delete);

        Optional<Learn> learn = learnRepository.findByMemberAndGrape(writer, getGrape);
        learn.ifPresent(learnRepository::delete);

        grapeRepository.delete(getGrape);

        return Response.of(HttpStatus.OK, "포도알 삭제 완료!");
    }



    private void checkMemberAuthority(MemberEntity writer) {
        if (writer.getAuthority() != MemberAccountType.ROLE_ADMIN) {
            throw new CustomException(MEMBER_NOT_AUTHORITY);
        }
    }


    private GrapeSeed getGrapeSeed(Long gpseId) {
        return grapeSeedRepository.findById(gpseId).orElseThrow(
                () -> new CustomException(GRAPESEED_NOT_EXIST));
    }

    private Grape getGrape(Long gpId) {
        return grapeRepository.findById(gpId).orElseThrow(
                () -> new CustomException(GRAPE_NOT_EXIST));
    }


    private List<GrapeSeed> getGpseList(List<Long> gpseIdList) {
        List<GrapeSeed> gpseList = new ArrayList<>();
        for (Long gpseId : gpseIdList) {
            GrapeSeed getGrapeSeed = getGrapeSeed(gpseId);
            gpseList.add(getGrapeSeed);
        }
        return gpseList;
    }


    private Integer getGpTime(List<GrapeSeed> grapeSeedList) {
        Integer getGpTime = 0;
        for (GrapeSeed grapeSeed : grapeSeedList) {
            getGpTime += grapeSeed.getGpseTime();
        }
        return getGpTime;
    }

    private Integer getGpExp(List<GrapeSeed> grapeSeedList) {
        Integer getGpExp = 0;
        for (GrapeSeed grapeSeed : grapeSeedList) {
            getGpExp += grapeSeed.getGpseExp();
        }
        return getGpExp;
    }


    private void checkLike(Optional<Like> like, Grape getGrape) {
        if (like.isEmpty()) {
            getGrape.GpLikeFalse();
        } else {
            getGrape.GpLikeTrue();
        }
    }

    private void checkLearn(Optional<Learn> learn, Grape getGrape) {
        if (learn.isEmpty()) {
            getGrape.GpTFFalse();
        } else {
            getGrape.GpTFTrue();
        }
    }

}
