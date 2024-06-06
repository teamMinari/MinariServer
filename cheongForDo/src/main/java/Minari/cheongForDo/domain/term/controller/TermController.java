package Minari.cheongForDo.domain.term.controller;

import Minari.cheongForDo.domain.term.dto.TermRequestDTO;
import Minari.cheongForDo.domain.term.dto.TermResponseDTO;
import Minari.cheongForDo.domain.term.model.service.TermService;
import Minari.cheongForDo.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Terms")
@RequiredArgsConstructor
public class TermController {
    private final TermService termService;

    // 용어 리스트 조회 (termNm으로 검색)
    @GetMapping("/search/{termNm}")
    public List<TermResponseDTO> getTerms(@PathVariable String termNm) {
        return termService.getTerms(termNm);
    }

    // 용어 등록
    @PostMapping
    public BaseResponse<?> createTerm(
            @RequestBody TermRequestDTO requestDTO
    ) {
        return termService.createTerm(requestDTO);
    }

    // 용어 하나 조회
    @GetMapping("{termNm}")
    public TermResponseDTO getTerm(@PathVariable String termNm) {
        return termService.findOneTerm(termNm);
    }

    // 용어 수정
    @PutMapping("{termNm}")
    public String updateTerm(@PathVariable String termNm, @RequestBody TermRequestDTO requestDTO) {
        return termService.update(termNm, requestDTO);
    }

    // 용어 삭제
    @DeleteMapping("{termNm}")
    public BaseResponse<?> deleteTerm(@PathVariable String termNm) {
        return termService.deleteTerm(termNm);
    }
}
