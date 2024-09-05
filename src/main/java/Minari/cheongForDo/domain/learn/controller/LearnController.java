package Minari.cheongForDo.domain.learn.controller;

import Minari.cheongForDo.domain.learn.enums.LearnCategory;
import Minari.cheongForDo.domain.learn.service.LearnService;
import Minari.cheongForDo.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/learn")
@RequiredArgsConstructor
@Tag(name = "LEARN", description = "learn API")
public class LearnController {
    private final LearnService learnService;

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "학습 여부 등록", description = "이거 하면 유저에 대한 학습 여부가 바뀌고, 이미 학습되었는데 또 요청하면 오류 뜸")
    public Response patch(@RequestParam LearnCategory category, @RequestParam Long id) {
        return learnService.toggle(category, id);
    }

}
