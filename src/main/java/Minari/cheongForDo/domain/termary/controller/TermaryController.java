package Minari.cheongForDo.domain.termary.controller;

import Minari.cheongForDo.domain.termary.service.TermaryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import Minari.cheongForDo.domain.termary.dto.TermaryRequestDTO;

@RequestMapping("/termary")
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "TERMARY", description = "termary API")
@RequiredArgsConstructor
public class TermaryController {

    private final TermaryService termaryService;

    @GetMapping("/summarize/{termNm}")
    public String summarizeTerm(@PathVariable String termNm) {
        TermaryRequestDTO termaryRequestDTO = new TermaryRequestDTO();
        termaryRequestDTO.setTermNm(termNm);
        return termaryService.summarizeTerm(termaryRequestDTO);
    }
}
