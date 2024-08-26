package Minari.cheongForDo.domain.like.service;

import Minari.cheongForDo.domain.like.repository.LikeRepository;
import Minari.cheongForDo.domain.like.entity.Like;
import Minari.cheongForDo.domain.member.entity.MemberEntity;
import Minari.cheongForDo.domain.term.entity.Term;
import Minari.cheongForDo.domain.term.repository.TermRepository;
import Minari.cheongForDo.global.auth.UserSessionHolder;
import Minari.cheongForDo.global.exception.CustomErrorCode;
import Minari.cheongForDo.global.exception.CustomException;
import Minari.cheongForDo.global.response.Response;
import Minari.cheongForDo.global.response.ResponseData;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService { // 대규모 수정 필요..
    private final LikeRepository likeRepository;
    private final UserSessionHolder userSessionHolder;
    private final TermRepository termRepository;

    public Response toggle(String word){
        MemberEntity curMember = userSessionHolder.current();

        Term term = termRepository.findById(word)
                .orElseThrow(()-> new CustomException(CustomErrorCode.TERM_NOT_EXIST));

        Optional<Like> like = likeRepository.findByMemberAndTerm(curMember, term);

        if(like.isEmpty()){
            addLike(curMember, term);
            return Response.of(HttpStatus.OK, "단어장 추가 성공");
        }
        else{
            likeRepository.delete(like.get());
            return Response.of(HttpStatus.OK, "단어장 삭제 성공");
        }

    }

    private void addLike(MemberEntity member, Term term){
        likeRepository.save(
                Like.builder()
                        .term(term)
                        .member(member)
                        .build()
        );
    }

    public ResponseData<List<Term>> getMy(){
        MemberEntity curMember = userSessionHolder.current();

        List<Like> likes =  likeRepository.findByMember(curMember);
        List<Term> terms = likes.stream()
                .map(Like::getTerm)
                .toList();

        return ResponseData.of(HttpStatus.OK, "단어장 조회 성공", terms);
    }
}
