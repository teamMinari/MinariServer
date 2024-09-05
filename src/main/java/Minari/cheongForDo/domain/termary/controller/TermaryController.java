package Minari.cheongForDo.domain.termary.controller;

import Minari.cheongForDo.domain.termary.service.TermaryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import Minari.cheongForDo.domain.termary.dto.TermaryRequestDTO;

@RequestMapping("/termary")
@RestController
@AllArgsConstructor
public class TermaryController {

    private final TermaryService termaryService;

    @GetMapping("/summarize/{termNm}")
    public String summarizeTerm(@PathVariable String termNm) {
        TermaryRequestDTO termaryRequestDTO = new TermaryRequestDTO();
        termaryRequestDTO.setTermNm(termNm);
        return termaryService.summarizeTerm(termaryRequestDTO);
    }
}
