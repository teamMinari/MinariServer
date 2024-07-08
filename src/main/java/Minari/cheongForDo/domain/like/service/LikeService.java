package Minari.cheongForDo.domain.like.service;

import Minari.cheongForDo.domain.like.repository.LikeRepository;
import Minari.cheongForDo.domain.like.entity.Like;
import Minari.cheongForDo.domain.member.entity.MemberEntity;
import Minari.cheongForDo.domain.term.entity.Term;
import Minari.cheongForDo.domain.term.repository.TermRepository;
import Minari.cheongForDo.global.auth.UserSessionHolder;
import Minari.cheongForDo.global.exception.CustomErrorCode;
import Minari.cheongForDo.global.exception.CustomException;
import Minari.cheongForDo.global.response.BaseResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserSessionHolder userSessionHolder;
    private final TermRepository termRepository;

    public BaseResponse<?> toggle(String word){
        MemberEntity curMember = userSessionHolder.current();

        Term term = termRepository.findById(word)
                .orElseThrow(()-> new CustomException(CustomErrorCode.TERM_NOT_EXIST));

        Optional<Like> like = likeRepository.findByMemberAndTerm(curMember, term);

        if(like.isEmpty()){
            addLike(curMember, term);
        }else{
            likeRepository.delete(like.get());
        }

        return BaseResponse.of(
                true,
                "OK",
                "단어장 추가/삭제 성공",
                null
        );
    }

    private void addLike(MemberEntity member, Term term){
        likeRepository.save(
                Like.builder()
                        .term(term)
                        .member(member)
                        .build()
        );
    }

    public BaseResponse<?> getMy(){
        MemberEntity curMember = userSessionHolder.current();
        List<Like> likes =  likeRepository.findByMember(curMember);
        List<Term> terms = likes.stream()
                .map(Like::getTerm)
                .toList();
        return BaseResponse.of(
                true,
                "OK",
                "단어장 조회 성공",
                terms
        );
    }
}
