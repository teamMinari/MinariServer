package Minari.cheongForDo.domain.term.model.service;

import Minari.cheongForDo.domain.member.authority.MemberAccountType;
import Minari.cheongForDo.domain.member.entity.MemberEntity;
import Minari.cheongForDo.domain.member.repository.MemberRepository;
import Minari.cheongForDo.domain.term.dto.TermRequestDTO;
import Minari.cheongForDo.domain.term.dto.TermResponseDTO;
import Minari.cheongForDo.domain.term.entity.Term;
import Minari.cheongForDo.domain.term.repository.TermRepository;
import Minari.cheongForDo.global.exception.CustomException;
import Minari.cheongForDo.global.response.BaseResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

import static Minari.cheongForDo.global.exception.CustomErrorCode.MEMBER_NOT_AUTHORITY;
import static Minari.cheongForDo.global.exception.CustomErrorCode.MEMBER_NOT_EXIST;
import static Minari.cheongForDo.global.exception.CustomErrorCode.TERM_NOT_EXIST;

@Service
@Transactional
@RequiredArgsConstructor
public class TermService {
        private final TermRepository termRepository;
        private final MemberRepository memberRepository;

        // 용어 전체 조회
        public List<TermResponseDTO> getTerms() {
                List<Term> termList = termRepository.findAll();
                return termList.stream().map(
                        TermResponseDTO::of
                ).toList();
        }

        // 용어 생성
        public BaseResponse<?> createTerm(TermRequestDTO requestDTO) {
                 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                MemberEntity member = memberRepository.findById(authentication.getName())
                        .orElseThrow(() -> new CustomException(MEMBER_NOT_EXIST));

                if (member.getAuthority() != MemberAccountType.ROLE_ADMIN) {
                        throw new CustomException(MEMBER_NOT_AUTHORITY);
                }

                Term term = Term.builder()
                        .termNm(requestDTO.getTermNm())
                        .termExplain(requestDTO.getTermExplain())
                        .termDifficulty(requestDTO.getTermDifficulty())
                        .termCategory(requestDTO.getTermCategory())
                        .build();
                termRepository.save(term);
                return BaseResponse.of(
                        true,
                        "OK",
                        "용어 생성 성공 !!",
                        null
                );
        }

        // 용어 하나 조회
        public TermResponseDTO findOneTerm(String termNm) {
                Term term = termRepository.findById(termNm).orElseThrow(
                        () -> new CustomException(TERM_NOT_EXIST)
                );
                return TermResponseDTO.of(term);
        }

        // 용어 수정
        @Transactional
        public String update(String termNm, TermRequestDTO requestDTO) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                MemberEntity member = memberRepository.findById(authentication.getName())
                        .orElseThrow(() -> new CustomException(MEMBER_NOT_EXIST));

                if (member.getAuthority() != MemberAccountType.ROLE_ADMIN) {
                        throw new CustomException(MEMBER_NOT_AUTHORITY);
                }

                Term term = termRepository.findById(termNm).orElseThrow(
                        () -> new CustomException(TERM_NOT_EXIST)

                );
                term.update(requestDTO);
                return term.getTermNm();
        }

        // 용어 삭제
        @Transactional
        public BaseResponse<?> deleteTerm(String termNm) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                MemberEntity member = memberRepository.findById(authentication.getName())
                        .orElseThrow(() -> new CustomException(MEMBER_NOT_EXIST));

                if (member.getAuthority() != MemberAccountType.ROLE_ADMIN) {
                        throw new CustomException(MEMBER_NOT_AUTHORITY);
                }

                Term term = termRepository.findById(termNm).orElseThrow(
                        () -> new CustomException(TERM_NOT_EXIST)
                );
                termRepository.deleteById(termNm);
                return BaseResponse.of(
                        true,
                        "OK",
                        "용어 삭제 성공 !!",
                        null
                );
        }


}
