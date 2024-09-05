package Minari.cheongForDo.domain.like.service;

import Minari.cheongForDo.domain.grape.dto.GrapeLikeLoadRes;
import Minari.cheongForDo.domain.grape.entity.Grape;
import Minari.cheongForDo.domain.grape.repository.GrapeRepository;
import Minari.cheongForDo.domain.grapeSeed.dto.GrapeSeedLikeLoadRes;
import Minari.cheongForDo.domain.grapeSeed.entity.GrapeSeed;
import Minari.cheongForDo.domain.grapeSeed.repository.GrapeSeedRepository;
import Minari.cheongForDo.domain.grapes.dto.GrapesLikeLoadRes;
import Minari.cheongForDo.domain.grapes.entity.Grapes;
import Minari.cheongForDo.domain.grapes.repository.GrapesRepository;
import Minari.cheongForDo.domain.like.enums.LikeCategory;
import Minari.cheongForDo.domain.like.repository.LikeRepository;
import Minari.cheongForDo.domain.like.entity.Like;
import Minari.cheongForDo.domain.member.entity.MemberEntity;
import Minari.cheongForDo.domain.term.dto.TermLikeLoadRes;
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

import java.util.List;
import java.util.Optional;

import static Minari.cheongForDo.global.exception.CustomErrorCode.GRAPESEED_NOT_EXIST;
import static Minari.cheongForDo.global.exception.CustomErrorCode.GRAPES_NOT_EXIST;
import static Minari.cheongForDo.global.exception.CustomErrorCode.GRAPE_NOT_EXIST;
import static Minari.cheongForDo.global.exception.CustomErrorCode.TERM_NOT_EXIST;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService { // 포도송이, 포도알 이후 추가
    private final LikeRepository likeRepository;
    private final UserSessionHolder userSessionHolder;
    private final TermRepository termRepository;
    private final GrapeSeedRepository grapeSeedRepository;
    private final GrapeRepository grapeRepository;
    private final GrapesRepository grapesRepository;

    public Response toggle(LikeCategory category, Long id) {
        MemberEntity curMember = userSessionHolder.current();

        if (category == LikeCategory.TERM) {
            likeTerm(curMember, id);
        } else if (category == LikeCategory.GRAPESEED) {
            likeGrapeSeed(curMember, id);
        } else if (category == LikeCategory.GRAPE) {
            likeGrape(curMember, id);
        } else {
            likeGrapes(curMember, id);
        }

        return Response.of(HttpStatus.OK, "좋아요 생성/취소 성공!");

    }


    @Transactional
    public ResponseData<List<TermLikeLoadRes>> myTerm() {
        MemberEntity curMember = userSessionHolder.current();

        List<Like> termLikes = likeRepository.findAllByMemberAndTermIsNotNull(curMember);

        List<TermLikeLoadRes> term = termLikes.stream()
                .map(like -> TermLikeLoadRes.of(like.getTerm()))
                .toList();

        return ResponseData.of(HttpStatus.OK, "저장된 용어 조회 성공!", term);

    }

    public ResponseData<List<GrapesLikeLoadRes>> myGrapes() {
        MemberEntity curMember = userSessionHolder.current();

        List<Like> grapesLikes = likeRepository.findAllByMemberAndGrapesIsNotNull(curMember);

        List<GrapesLikeLoadRes> grapesRes = grapesLikes.stream()
                .map(like -> GrapesLikeLoadRes.of(like.getGrapes()))
                .toList();

        return ResponseData.of(HttpStatus.OK, "저장된 포도송이 조회 성공!", grapesRes);
    }

    public ResponseData<List<GrapeLikeLoadRes>> myGrape() {
        MemberEntity curMember = userSessionHolder.current();

        List<Like> grapeLikes = likeRepository.findAllByMemberAndGrapeIsNotNull(curMember);

        List<GrapeLikeLoadRes> grapeRes = grapeLikes.stream()
                .map(like -> GrapeLikeLoadRes.of(like.getGrape()))
                .toList();

        return ResponseData.of(HttpStatus.OK, "저장된 포도알 조회 성공!", grapeRes);
    }

    public ResponseData<List<GrapeSeedLikeLoadRes>> myGrapeSeed() {
        MemberEntity curMember = userSessionHolder.current();

        List<Like> grapeSeedLikes = likeRepository.findAllByMemberAndGrapeSeedIsNotNull(curMember);

        List<GrapeSeedLikeLoadRes> grapeSeedRes = grapeSeedLikes.stream()
                .map(like -> GrapeSeedLikeLoadRes.of(like.getGrapeSeed()))
                .toList();

        return ResponseData.of(HttpStatus.OK, "저장된 포도씨 조회 성공!", grapeSeedRes);
    }


    private void likeTerm(MemberEntity curMember, Long termId) {
        Term getTerm = getTerm(termId);

        Optional<Like> like = likeRepository.findByMemberAndTerm(curMember, getTerm);

        if (like.isEmpty()) {
            addTermLike(curMember, getTerm);
        } else {
            likeRepository.delete(like.get());
        }
    }

    private void likeGrapeSeed(MemberEntity curMember, Long gpseId) {
        GrapeSeed getGrapeSeed = getGrapeSeed(gpseId);

        Optional<Like> like = likeRepository.findByMemberAndGrapeSeed(curMember, getGrapeSeed);

        if (like.isEmpty()) {
            addGrapeSeedLike(curMember, getGrapeSeed);
        } else {
            likeRepository.delete(like.get());
        }
    }

    private void likeGrape(MemberEntity curMember, Long gpId) {
        Grape getGrape = getGrape(gpId);

        Optional<Like> like = likeRepository.findByMemberAndGrape(curMember, getGrape);

        if (like.isEmpty()) {
            addGrapeLike(curMember, getGrape);
        } else {
            likeRepository.delete(like.get());
        }
    }

    private void likeGrapes(MemberEntity curMember, Long gpsId) {
        Grapes getGrapes = getGrapes(gpsId);

        Optional<Like> like = likeRepository.findByMemberAndGrapes(curMember, getGrapes);

        if (like.isEmpty()) {
            addGrapesLike(curMember, getGrapes);
        } else {
            likeRepository.delete(like.get());
        }
    }


    private void addTermLike(MemberEntity member, Term term) {
        likeRepository.save(
                Like.builder()
                        .member(member)
                        .term(term)
                        .build()
        );
    }

    private void addGrapeSeedLike(MemberEntity member, GrapeSeed grapeSeed) {
        likeRepository.save(
                Like.builder()
                        .member(member)
                        .grapeSeed(grapeSeed)
                        .build()
        );
    }

    private void addGrapeLike(MemberEntity member, Grape grape) {
        likeRepository.save(
                Like.builder()
                        .member(member)
                        .grape(grape)
                        .build()
        );
    }

    private void addGrapesLike(MemberEntity member, Grapes grapes) {
        likeRepository.save(
                Like.builder()
                        .member(member)
                        .grapes(grapes)
                        .build()
        );
    }


    private Term getTerm(Long termId) {
        return termRepository.findById(termId).orElseThrow(
                () -> new CustomException(TERM_NOT_EXIST)
        );
    }

    private GrapeSeed getGrapeSeed(Long gpseId) {
        return grapeSeedRepository.findById(gpseId).orElseThrow(
                () -> new CustomException(GRAPESEED_NOT_EXIST));
    }

    private Grape getGrape(Long gpId) {
        return grapeRepository.findById(gpId).orElseThrow(
                () -> new CustomException(GRAPE_NOT_EXIST));
    }

    private Grapes getGrapes(Long gpsId) {
        return grapesRepository.findById(gpsId).orElseThrow(
                () -> new CustomException(GRAPES_NOT_EXIST)
        );
    }
}

