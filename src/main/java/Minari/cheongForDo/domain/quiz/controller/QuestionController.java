package Minari.cheongForDo.domain.quiz.controller;

import Minari.cheongForDo.domain.quiz.dto.QuestionRequestDTO;
import Minari.cheongForDo.domain.quiz.dto.QuestionResponseDTO;
import Minari.cheongForDo.domain.quiz.model.service.QuestionService;
import Minari.cheongForDo.global.response.Response;
import Minari.cheongForDo.global.response.ResponseData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "QUESTION", description = "question API")
public class QuestionController {

    private final QuestionService questionService;

    // 질문 전체 조회
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "질문 전체 조회")
    public ResponseData<List<QuestionResponseDTO>> getQuestions() {
        return questionService.getQuestions();
    }

    // 질문 난이도 별 조회
    @GetMapping("/level/{level}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "질문 난이도 별 조회")
    public ResponseData<List<QuestionResponseDTO>> getLevelQuestions(@PathVariable Long level) {
        return questionService.getLevelQuestions(level);
    }

    // 질문 등록
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "질문 등록")
    public Response createQuestion(@RequestBody QuestionRequestDTO requestDTO) {
        return questionService.createQuestion(requestDTO);
    }

    // 질문 일일 조회
    @GetMapping("{questionIdx}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "질문 일일 조회")
    public ResponseData<QuestionResponseDTO> getQuestion(@PathVariable Long questionIdx) {
        return questionService.findOneQuestion(questionIdx);
    }

    // 질문 수정
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "질문 업데이트")
    public ResponseData<Long> updateQuestion(@RequestParam Long qtIdx, @RequestBody QuestionRequestDTO requestDTO) {
        return questionService.update(qtIdx, requestDTO);
    }

    // 질문 삭제
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "질문 삭제")
    public Response deleteQuestion(@RequestParam Long qtIdx) {
        return questionService.deleteQuestion(qtIdx);
    }

}
