package Minari.cheongForDo.domain.term.controller;

import Minari.cheongForDo.domain.term.dto.TermRequestDTO;
import Minari.cheongForDo.domain.term.dto.TermResponseDTO;
import Minari.cheongForDo.domain.term.model.service.TermService;
import Minari.cheongForDo.global.response.Response;
import Minari.cheongForDo.global.response.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    @GetMapping
    public ResponseData<List<TermResponseDTO>> getTerms() {
        return termService.getTerms();
    }

    // 용어 난이도 별 조회
    @GetMapping("/level/{level}")
    public ResponseData<List<TermResponseDTO>> getLevelTerms(@PathVariable Long level) {
        return termService.getLevelTerms(level);
    }

    // 용어 등록
    @PostMapping
    public Response createTerm(@RequestBody TermRequestDTO requestDTO) {
        return termService.createTerm(requestDTO);
    }

    // 용어 하나 조회
    @GetMapping("{termNm}")
    public ResponseData<TermResponseDTO> getTerm(@PathVariable String termNm) {
        return termService.findOneTerm(termNm);
    }

    // 용어 수정
    @PatchMapping
    public ResponseData<String> updateTerm(@RequestParam String termNm, @RequestBody TermRequestDTO requestDTO) {
        return termService.update(termNm, requestDTO);
    }

    // 용어 삭제
    @DeleteMapping
    public Response deleteTerm(@RequestParam String termNm) {
        return termService.deleteTerm(termNm);
    }

}
