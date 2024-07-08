package Minari.cheongForDo.domain.term.controller;

import Minari.cheongForDo.domain.term.dto.TermRequestDTO;
import Minari.cheongForDo.domain.term.dto.TermResponseDTO;
import Minari.cheongForDo.domain.term.model.service.TermService;
import Minari.cheongForDo.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/terms")
@RequiredArgsConstructor
public class TermController {
    private final TermService termService;

    // 용어 전체 조회
    @GetMapping("/all")
    public List<TermResponseDTO> getTerms() {
        return termService.getTerms();
    }

    // 용어 등록
    @PostMapping
    public BaseResponse<?> createTerm(@RequestBody TermRequestDTO requestDTO) {
        return termService.createTerm(requestDTO);
    }

    // 용어 하나 조회
    @GetMapping
    public TermResponseDTO getTerm(@RequestParam String termNm) {
        return termService.findOneTerm(termNm);
    }

    // 용어 수정
    @PutMapping
    public String updateTerm(@RequestParam String termNm, @RequestBody TermRequestDTO requestDTO) {
        return termService.update(termNm, requestDTO);
    }

    // 용어 삭제
    @DeleteMapping
    public BaseResponse<?> deleteTerm(@RequestParam String termNm) {
        return termService.deleteTerm(termNm);
    }
}
