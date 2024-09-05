package Minari.cheongForDo.domain.learn.controller;

import Minari.cheongForDo.domain.learn.enums.LearnCategory;
import Minari.cheongForDo.domain.learn.service.LearnService;
import Minari.cheongForDo.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/learn")
@RequiredArgsConstructor
public class LearnController {
    private final LearnService learnService;

    @PatchMapping
    public Response patch(@RequestParam LearnCategory category, @RequestParam Long id) {
        return learnService.toggle(category, id);
    }

}
