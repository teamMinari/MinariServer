package Minari.cheongForDo.domain.quiz.model.service;

import Minari.cheongForDo.domain.member.authority.MemberAccountType;
import Minari.cheongForDo.domain.member.entity.MemberEntity;
import Minari.cheongForDo.domain.quiz.dto.QuestionRequestDTO;
import Minari.cheongForDo.domain.quiz.dto.QuestionResponseDTO;
import Minari.cheongForDo.domain.quiz.entity.Question;
import Minari.cheongForDo.domain.quiz.model.enums.QuestionDifficulty;
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

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserSessionHolder userSessionHolder;

    // 질문 전체 조회
    public ResponseData<List<QuestionResponseDTO>> getQuestions() {

        List<Question> questionLists = questionRepository.findAll();

        return ResponseData.of(HttpStatus.OK, "질문 전체 조회 성공!",
                questionLists.stream().map(
                        QuestionResponseDTO::of
                ).toList());
    }

    // 질문 난이도 별 조회
    public ResponseData<List<QuestionResponseDTO>> getLevelQuestions(Long level) {

        List<Question> questionList = checkQuestionLevel(level);

        return ResponseData.of(HttpStatus.OK, "질문 난이도 별 조회 성공!",
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
                .qtTip(createDTO.getQtTip())
                .qtDifficulty(createDTO.getQtDifficulty())
                .build();

        questionRepository.save(question);

        return Response.of(HttpStatus.OK, "질문 생성 성공!");
    }

    // 질문 하나 조회
    public ResponseData<QuestionResponseDTO> findOneQuestion(Long qtIdx) {

        Question getQuestion = getQuestion(qtIdx);

        return ResponseData.of(HttpStatus.OK,
                "질문 조회 성공!",
                QuestionResponseDTO.of(getQuestion));
    }

    // 질문 수정
    public ResponseData<Long> update(Long qtIdx, QuestionRequestDTO requestDTO) {
        MemberEntity curMember = userSessionHolder.current();

        checkMemberAuthority(curMember);

        Question getQuestion = getQuestion(qtIdx);

        getQuestion.update(requestDTO);

        return ResponseData.of(HttpStatus.OK, "질문 수정 성공!", getQuestion.getQtIdx());
    }

    // 질문 삭제
    @Transactional
    public Response deleteQuestion(Long qtIdx) {
        MemberEntity curMember = userSessionHolder.current();

        checkMemberAuthority(curMember);

        Question getQuestion = getQuestion(qtIdx);

        questionRepository.delete(getQuestion);
        return Response.of(HttpStatus.OK, "질문 삭제 성공!");
    }

    private void checkMemberAuthority(MemberEntity curMember) {
        if (curMember.getAuthority() != MemberAccountType.ROLE_ADMIN) {
            throw new CustomException(MEMBER_NOT_AUTHORITY);
        }
    }

    private List<Question> checkQuestionLevel(Long level) {

        if (level.intValue() == 1) {
            return questionRepository.findAllByQtDifficulty(QuestionDifficulty.LV_1);
        }
        else if (level.intValue() == 2) {
            return questionRepository.findAllByQtDifficulty(QuestionDifficulty.LV_2);
        }
        else {
            return questionRepository.findAllByQtDifficulty(QuestionDifficulty.LV_3);
        }

    }

    private Question getQuestion(Long qtIdx) {
        return questionRepository.findById(qtIdx).orElseThrow(
                () -> new CustomException(QUESTION_NOT_EXIST)
        );
    }
}
