package Minari.cheongForDo.domain.quiz.model.service;

import Minari.cheongForDo.domain.quiz.dto.QuestionRequestDTO;
import Minari.cheongForDo.domain.quiz.dto.QuestionResponseDTO;
import Minari.cheongForDo.domain.quiz.entity.Question;
import Minari.cheongForDo.domain.quiz.repository.QuestionRepository;
import Minari.cheongForDo.domain.term.dto.TermResponseDTO;
import Minari.cheongForDo.global.exception.CustomException;
import Minari.cheongForDo.global.response.BaseResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    // 질문 전체 조회
    public List<QuestionResponseDTO> getQuestions() {
        List<Question> questionList = questionRepository.findAll();
        return questionList.stream().map(
                QuestionResponseDTO::of
        ).toList();
    }

    // 질문 생성
    public BaseResponse<?> createQuestion(QuestionRequestDTO createDTO) {
        Question question = Question.builder()
                .qtContents(createDTO.getQtContents())
                .qtAnswer(createDTO.getQtAnswer())
                .qtCmt(createDTO.getQtCmt())
                .qtDifficulty(createDTO.getQtDifficulty())
                .build();

        questionRepository.save(question);

        return BaseResponse.of(
                true,
                "OK",
                "질문 생성 성공 !!",
                null
        );
    }

    // 질문 하나 조회
    public QuestionResponseDTO findOneQuestion(Long qtIdx) {
        Question question = questionRepository.findById(qtIdx).orElseThrow(
                () -> new CustomException(QUESTION_NOT_EXIST)
        );
        return QuestionResponseDTO.of(question);
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
