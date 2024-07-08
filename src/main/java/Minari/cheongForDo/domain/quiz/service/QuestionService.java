package Minari.cheongForDo.domain.quiz.service;

import Minari.cheongForDo.domain.quiz.dto.QuestionRequestDTO;
import Minari.cheongForDo.domain.quiz.entity.Question;
import Minari.cheongForDo.domain.quiz.repository.QuestionRepository;
import Minari.cheongForDo.global.exception.CustomException;
import Minari.cheongForDo.global.response.BaseResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static Minari.cheongForDo.global.exception.CustomErrorCode.QUESTION_NOT_EXIST;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService { // 질문 일일, 전체 조회 필요합니다.

    private final QuestionRepository questionRepository;

    // 질문 생성
    public BaseResponse<?> createQuestion(QuestionRequestDTO createDTO) {
        Question question = Question.builder()
                .qtName(createDTO.getQtName())
                .qtContents(createDTO.getQtContents())
                .qtAnswer(createDTO.getQtAnswer())
                .qtCmt(createDTO.getQtCmt())
                .qtTerm(createDTO.getQtTerm())
                .build();

        questionRepository.save(question);

        return BaseResponse.of(
                true,
                "OK",
                "질문 생성 성공 !!",
                null
        );
    }

    // 질문 수정
    public Long update(Long qtIdx, QuestionRequestDTO requestDTO) {
        Question question = questionRepository.findById(qtIdx).orElseThrow(
                () -> new CustomException(QUESTION_NOT_EXIST)
        );
        question.update(requestDTO);

        return question.getQtIdx();
    }

    // 질문 삭제
    @Transactional
    public BaseResponse<?> deleteQuestion(Long qtIdx) {
        Question question = questionRepository.findById(qtIdx)
                .orElseThrow(() -> new CustomException(QUESTION_NOT_EXIST));

        questionRepository.deleteById(qtIdx);
        return BaseResponse.of(
                true,
                "OK",
                "질문 삭제 성공 !!",
                null
        );
    }
}
