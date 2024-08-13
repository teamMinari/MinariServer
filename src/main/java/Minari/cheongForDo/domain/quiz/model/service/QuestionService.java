package Minari.cheongForDo.domain.quiz.model.service;

import Minari.cheongForDo.domain.member.authority.MemberAccountType;
import Minari.cheongForDo.domain.member.entity.MemberEntity;
import Minari.cheongForDo.domain.quiz.dto.QuestionRequestDTO;
import Minari.cheongForDo.domain.quiz.dto.QuestionResponseDTO;
import Minari.cheongForDo.domain.quiz.entity.Question;
import Minari.cheongForDo.domain.quiz.repository.QuestionRepository;
import Minari.cheongForDo.global.auth.UserSessionHolder;
import Minari.cheongForDo.global.exception.CustomException;
import Minari.cheongForDo.global.response.Response;
import Minari.cheongForDo.global.response.ResponseData;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static Minari.cheongForDo.global.exception.CustomErrorCode.MEMBER_NOT_AUTHORITY;
import static Minari.cheongForDo.global.exception.CustomErrorCode.QUESTION_NOT_EXIST;


/*
"quiz": {
    "questions": [1, 2, 3, 4, ...]
} // NoSQL (Mongodb, redis, ...etc) <<

QZ   QT   QZ-QT
1    1-10  1-1 1-2 1-3 1-4 ... 1-10 // Relational (MySQL, MariaDB, ...etc)
* */

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService { //

    private final QuestionRepository questionRepository;
    private final UserSessionHolder userSessionHolder;

    // 질문 전체 조회
    public ResponseData<List<QuestionResponseDTO>> getQuestions() {

        List<Question> questionList = questionRepository.findAll();

        return ResponseData.of(HttpStatus.OK, "질문 전체 조회 성공!",
                questionList.stream().map(
                QuestionResponseDTO::of
        ).toList());
    }

    // 질문 생성
    public Response createQuestion(QuestionRequestDTO createDTO) {
        MemberEntity curMember = userSessionHolder.current();

        checkMemberAuthority(curMember);

        Question question = Question.builder()
                .qtContents(createDTO.getQtContents())
                .qtAnswer(createDTO.getQtAnswer())
                .qtCmt(createDTO.getQtCmt())
                .qtDifficulty(createDTO.getQtDifficulty())
                .build();

        questionRepository.save(question);

        return Response.of(HttpStatus.OK, "질문 생성 성공 !");
    }

    // 질문 하나 조회
    public ResponseData<QuestionResponseDTO> findOneQuestion(Long qtIdx) {

        Question question = questionRepository.findById(qtIdx).orElseThrow(
                () -> new CustomException(QUESTION_NOT_EXIST)
        );

        return ResponseData.of(HttpStatus.OK,
                "질문 하나 조회 성공!",
                QuestionResponseDTO.of(question));
    }

    // 질문 수정
    public ResponseData<Long> update(Long qtIdx, QuestionRequestDTO requestDTO) {
        MemberEntity curMember = userSessionHolder.current();

        checkMemberAuthority(curMember);

        Question question = questionRepository.findById(qtIdx).orElseThrow(
                () -> new CustomException(QUESTION_NOT_EXIST)
        );

        question.update(requestDTO);

        return ResponseData.of(HttpStatus.OK, "질문 수정 성공!", question.getQtIdx());
    }

    // 질문 삭제
    @Transactional
    public Response deleteQuestion(Long qtIdx) {
        MemberEntity curMember = userSessionHolder.current();

        checkMemberAuthority(curMember);

        Question question = questionRepository.findById(qtIdx)
                .orElseThrow(() -> new CustomException(QUESTION_NOT_EXIST));

        questionRepository.delete(question);
        return Response.of(HttpStatus.OK, "질문 삭제 성공 !");
    }

    private void checkMemberAuthority(MemberEntity curMember) {
        if (curMember.getAuthority() != MemberAccountType.ROLE_ADMIN) {
            throw new CustomException(MEMBER_NOT_AUTHORITY);
        }
    }
}
