package Minari.cheongForDo.domain.quiz.controller;

import Minari.cheongForDo.domain.quiz.dto.QuestionRequestDTO;
import Minari.cheongForDo.domain.quiz.service.QuestionService;
import Minari.cheongForDo.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/create")
    public BaseResponse<?> createQuestion(@RequestBody QuestionRequestDTO requestDTO) {
        return questionService.createQuestion(requestDTO);
    }

    @PutMapping("/update{qtIdx}")
    public Long updateQuestion(@PathVariable Long qtIdx, @RequestBody QuestionRequestDTO requestDTO) {
        return questionService.update(qtIdx, requestDTO);
    }


}
