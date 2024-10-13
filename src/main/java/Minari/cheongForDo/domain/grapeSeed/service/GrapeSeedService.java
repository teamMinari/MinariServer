package Minari.cheongForDo.domain.grapeSeed.service;

import Minari.cheongForDo.domain.grapeSeed.dto.GrapeSeedCommandReq;
import Minari.cheongForDo.domain.grapeSeed.dto.GrapeSeedLoadRes;
import Minari.cheongForDo.domain.grapeSeed.dto.GrapeSeedUpdateReq;
import Minari.cheongForDo.domain.grapeSeed.entity.GrapeSeed;
import Minari.cheongForDo.domain.grapeSeed.repository.GrapeSeedRepository;
import Minari.cheongForDo.domain.learn.entity.Learn;
import Minari.cheongForDo.domain.learn.repository.LearnRepository;
import Minari.cheongForDo.domain.like.entity.Like;
import Minari.cheongForDo.domain.like.repository.LikeRepository;
import Minari.cheongForDo.domain.member.authority.MemberAccountType;
import Minari.cheongForDo.domain.member.entity.MemberEntity;
import Minari.cheongForDo.domain.term.entity.Term;
import Minari.cheongForDo.domain.term.repository.TermRepository;
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
import static Minari.cheongForDo.global.exception.CustomErrorCode.MEMBER_NOT_AUTHORITY;
import static Minari.cheongForDo.global.exception.CustomErrorCode.TERM_NOT_EXIST;

@Service
@Transactional
@RequiredArgsConstructor
public class GrapeSeedService {

    private final GrapeSeedRepository grapeSeedRepository;
    private final UserSessionHolder userSessionHolder;
    private final LikeRepository likeRepository;
    private final LearnRepository learnRepository;
    private final TermRepository termRepository;

    public ResponseData<GrapeSeedLoadRes> findGrapeSeed(Long gpseId) {
        MemberEntity curMember = userSessionHolder.current();

        GrapeSeed getGrapeSeed = getGrapeSeed(gpseId);

        Optional<Like> like = likeRepository.findByMemberAndGrapeSeed(curMember, getGrapeSeed);
        Optional<Learn> learn = learnRepository.findByMemberAndGrapeSeed(curMember, getGrapeSeed);

        checkLike(like, getGrapeSeed);
        checkLearn(learn, getGrapeSeed);

        return ResponseData.of(HttpStatus.OK, "포도씨 조회 성공", GrapeSeedLoadRes.of(getGrapeSeed));
    }

    public Response createGrapeSeed(GrapeSeedCommandReq commandReq) {

        MemberEntity writer = userSessionHolder.current();
        checkMemberAuthority(writer);

        List<Term> getTermList = getTermList(commandReq.termIdList());

        GrapeSeed grapeSeed = commandReq.toEntity(getTermList);

        grapeSeedRepository.save(grapeSeed);

        return Response.of(HttpStatus.OK, "포도씨 생성 완료!");
    }

    public Response updateGrapeSeed(Long gpseId, GrapeSeedUpdateReq updateReq) {

        MemberEntity writer = userSessionHolder.current();
        checkMemberAuthority(writer);

        GrapeSeed getGrapeSeed = getGrapeSeed(gpseId);
        getGrapeSeed.update(updateReq);

        return Response.of(HttpStatus.OK, "포도씨 업데이트 완료!");
    }

    public Response updateGrapeSeedVerification(Long gpseId, String verification) {
        MemberEntity writer = userSessionHolder.current();
        checkMemberAuthority(writer);

        GrapeSeed getGrapeSeed = getGrapeSeed(gpseId);
        getGrapeSeed.verUpdate(verification);

        return Response.of(HttpStatus.OK, "포도씨 검증 업데이트 완료!");
    }

    public Response deleteGrapeSeed(Long gpseId) {

        MemberEntity writer = userSessionHolder.current();
        checkMemberAuthority(writer);

        GrapeSeed getGrapeSeed = getGrapeSeed(gpseId);

        Optional<Like> like = likeRepository.findByMemberAndGrapeSeed(writer, getGrapeSeed);
        like.ifPresent(likeRepository::delete);

        Optional<Learn> learn = learnRepository.findByMemberAndGrapeSeed(writer, getGrapeSeed);
        learn.ifPresent(learnRepository::delete);

        grapeSeedRepository.delete(getGrapeSeed);

        return Response.of(HttpStatus.OK, "포도씨 삭제 완료!");
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

    private Term getBoard(Long termId) {
        return termRepository.findById(termId).orElseThrow(
                () -> new CustomException(TERM_NOT_EXIST)
        );
    }

    private List<Term> getTermList(List<Long> termIdList) {
        List<Term> termList = new ArrayList<>();
        for (Long termId : termIdList) {
            Term getTerm = getBoard(termId);
            termList.add(getTerm);
        }
        return termList;
    }

    private void checkLike(Optional<Like> like, GrapeSeed getGrapeSeed) {
        if (like.isEmpty()) {
            getGrapeSeed.gpseLikeFalse();
        } else {
            getGrapeSeed.gpseLikeTrue();
        }
    }

    private void checkLearn(Optional<Learn> learn, GrapeSeed getGrapeSeed) {
        if (learn.isEmpty()) {
            getGrapeSeed.gpseTFFalse();
        } else {
            getGrapeSeed.gpseTFTrue();
        }
    }
}
