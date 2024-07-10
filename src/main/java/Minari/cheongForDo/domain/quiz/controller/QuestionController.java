package Minari.cheongForDo.domain.quiz.controller;

import Minari.cheongForDo.domain.quiz.dto.QuestionRequestDTO;
import Minari.cheongForDo.domain.quiz.dto.QuestionResponseDTO;
import Minari.cheongForDo.domain.quiz.model.service.QuestionService;
import Minari.cheongForDo.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    // 질문 전체 조회
    @GetMapping("/all")
    public List<QuestionResponseDTO> getTerms() {
        return questionService.getQuestions();
    }

    // 질문 등록
    @PostMapping
    public BaseResponse<?> createQuestion(@RequestBody QuestionRequestDTO requestDTO) {
        return questionService.createQuestion(requestDTO);
    }

    // 질문 하나 조회
    @GetMapping
    public QuestionResponseDTO getQuestion(@RequestParam Long qtIdx) {
        return questionService.findOneQuestion(qtIdx);
    }

    // 질문 수정
    @PutMapping
    public Long updateQuestion(@RequestParam Long qtIdx, @RequestBody QuestionRequestDTO requestDTO) {
        return questionService.update(qtIdx, requestDTO);
    }

    // 질문 삭제
    @DeleteMapping
    public BaseResponse<?> deleteQuestion(@RequestParam Long qtIdx) {
        return questionService.deleteQuestion(qtIdx);
    }

}
