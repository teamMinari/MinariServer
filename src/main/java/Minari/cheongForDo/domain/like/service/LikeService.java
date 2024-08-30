package Minari.cheongForDo.domain.like.service;

import Minari.cheongForDo.domain.grapeSeed.entity.GrapeSeed;
import Minari.cheongForDo.domain.grapeSeed.repository.GrapeSeedRepository;
import Minari.cheongForDo.domain.like.enums.LikeCategory;
import Minari.cheongForDo.domain.like.repository.LikeRepository;
import Minari.cheongForDo.domain.like.entity.Like;
import Minari.cheongForDo.domain.member.entity.MemberEntity;
import Minari.cheongForDo.domain.term.entity.Term;
import Minari.cheongForDo.domain.term.repository.TermRepository;
import Minari.cheongForDo.global.auth.UserSessionHolder;
import Minari.cheongForDo.global.exception.CustomException;
import Minari.cheongForDo.global.response.Response;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static Minari.cheongForDo.global.exception.CustomErrorCode.GRAPESEED_NOT_EXIST;
import static Minari.cheongForDo.global.exception.CustomErrorCode.TERM_NOT_EXIST;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService { // 포도송이, 포도알 이후 추가
    private final LikeRepository likeRepository;
    private final UserSessionHolder userSessionHolder;
    private final TermRepository termRepository;
    private final GrapeSeedRepository grapeSeedRepository;

    public Response toggle(LikeCategory category, Long id) {
        MemberEntity curMember = userSessionHolder.current();

        if (category == LikeCategory.TERM) {
            likeTerm(curMember, id);
        } else if (category == LikeCategory.GRAPESEED) {
            likeGrapeSeed(curMember, id);
        }

        return Response.of(HttpStatus.OK, "좋아요 생성/취소 성공!");

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


    private Term getTerm(Long termId) {
        return termRepository.findById(termId).orElseThrow(
                () -> new CustomException(TERM_NOT_EXIST)
        );
    }

    private GrapeSeed getGrapeSeed(Long gpseId) {
        return grapeSeedRepository.findById(gpseId).orElseThrow(
                () -> new CustomException(GRAPESEED_NOT_EXIST));
    }

}

