package Minari.cheongForDo.domain.term.model.service;

import Minari.cheongForDo.domain.like.entity.Like;
import Minari.cheongForDo.domain.like.repository.LikeRepository;
import Minari.cheongForDo.domain.member.authority.MemberAccountType;
import Minari.cheongForDo.domain.member.entity.MemberEntity;
import Minari.cheongForDo.domain.term.dto.TermRequestDTO;
import Minari.cheongForDo.domain.term.dto.TermResponseDTO;
import Minari.cheongForDo.domain.term.entity.Term;
import Minari.cheongForDo.domain.term.model.enums.TermDifficulty;
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

import static Minari.cheongForDo.global.exception.CustomErrorCode.MEMBER_NOT_AUTHORITY;
import static Minari.cheongForDo.global.exception.CustomErrorCode.TERM_NOT_EXIST;

@Service
@Transactional
@RequiredArgsConstructor
public class TermService {

        private final LikeRepository likeRepository;
        private final TermRepository termRepository;
        private final UserSessionHolder userSessionHolder;

        // 용어 전체 조회
        public ResponseData<List<TermResponseDTO>> getTerms(TermDifficulty level) {

                List<Term> termList = termRepository.findAllByTermDifficulty(level);

                return ResponseData.of(HttpStatus.OK, "용어 난이도 별 조회 성공!",
                        termList.stream().map(
                        TermResponseDTO::of
                ).toList());
        }

        // 용어 생성
        public Response createTerm(TermRequestDTO requestDTO) {
                MemberEntity curMember = userSessionHolder.current();

                checkMemberAuthority(curMember);

                Term term = Term.builder()
                        .termNm(requestDTO.getTermNm())
                        .termExplain(requestDTO.getTermExplain())
                        .termDifficulty(requestDTO.getTermDifficulty())
                        .build();

                termRepository.save(term);

                return Response.of(HttpStatus.OK, "용어 생성 성공!");
        }

        // 용어 하나 조회
        public ResponseData<TermResponseDTO> findOneTerm(String termNm) {

                Term term = termRepository.findById(termNm).orElseThrow(
                        () -> new CustomException(TERM_NOT_EXIST)
                );

                return ResponseData.of(HttpStatus.OK, "용어 조회 성공!", TermResponseDTO.of(term));
        }

        // 용어 수정
        @Transactional
        public ResponseData<String> update(String termNm, TermRequestDTO requestDTO) {
                MemberEntity curMember = userSessionHolder.current();

                checkMemberAuthority(curMember);

                Term term = termRepository.findById(termNm).orElseThrow(
                        () -> new CustomException(TERM_NOT_EXIST)

                );

                term.update(requestDTO);

                return ResponseData.of(HttpStatus.OK, "용어 수정 성공!", term.getTermNm());
        }

        // 용어 삭제
        @Transactional
        public Response deleteTerm(String termNm) {
                MemberEntity curMember = userSessionHolder.current();

                checkMemberAuthority(curMember);

                Term term = termRepository.findById(termNm).orElseThrow(
                        () -> new CustomException(TERM_NOT_EXIST)
                );

                Optional<Like> like = likeRepository.findByMemberAndTerm(curMember, term);
                like.ifPresent(likeRepository::delete);

                termRepository.delete(term);

                return Response.of(HttpStatus.OK, "용어 삭제 성공!");
        }

        private void checkMemberAuthority(MemberEntity curMember) {
                if (curMember.getAuthority() != MemberAccountType.ROLE_ADMIN) {
                        throw new CustomException(MEMBER_NOT_AUTHORITY);
                }
        }

}
