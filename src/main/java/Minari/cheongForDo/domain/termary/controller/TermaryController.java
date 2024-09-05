package Minari.cheongForDo.domain.termary.controller;

import Minari.cheongForDo.domain.termary.service.TermaryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import Minari.cheongForDo.domain.termary.dto.TermaryRequestDTO;

@RequestMapping("/termary")
@RestController
@AllArgsConstructor
public class TermaryController {
    private final TermaryService termaryService;

    @PostMapping("/summarize")
    public String summarizeTerm(@RequestBody TermaryRequestDTO termaryRequestDTO) {
        return termaryService.summarizeTerm(termaryRequestDTO);
    }
}
