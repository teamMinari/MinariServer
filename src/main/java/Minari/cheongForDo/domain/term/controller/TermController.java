package Minari.cheongForDo.domain.term.controller;

import Minari.cheongForDo.domain.term.dto.TermOneLoadLikeRes;
import Minari.cheongForDo.domain.term.dto.TermRequestDTO;
import Minari.cheongForDo.domain.term.dto.TermResponseDTO;
import Minari.cheongForDo.domain.term.model.service.TermService;
import Minari.cheongForDo.global.response.Response;
import Minari.cheongForDo.global.response.ResponseData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/terms")
@RequiredArgsConstructor
@Tag(name = "TERM", description = "term API")
public class TermController {
    private final TermService termService;

    // 용어 전체 조회
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "용어 전체 조회", description = "아무것도 안 넣으면 20개씩 보여줍니다.")
    public ResponseData<List<TermResponseDTO>> getTerms(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        return termService.getTerms(page, size);
    }

    // 용어 난이도 별 조회
    @GetMapping("/level/{level}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "용어 난이도 별 조회")
    public ResponseData<List<TermResponseDTO>> getLevelTerms(@PathVariable Long level) {
        return termService.getLevelTerms(level);
    }

    // 용어 등록
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "용어 등록")
    public Response createTerm(@RequestBody TermRequestDTO requestDTO) {
        return termService.createTerm(requestDTO);
    }

    // 용어 하나 조회
    @GetMapping("{termId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "용어 일일 조회")
    public ResponseData<TermOneLoadLikeRes> getTerm(@PathVariable Long termId) {
        return termService.findOneTerm(termId);
    }

    // 용어 이름으로 조회
    @GetMapping("/name/{termNm}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "용어 이름으로 조회", description = "이거 포도씨 안에 들어있는 용어 이름으로 조회하면 됩니다.")
    public ResponseData<TermResponseDTO> getTermWithNm(@PathVariable String termNm) {
        return termService.getTermsWithNm(termNm);
    }

    // 용어 수정
    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "용어 업데이트")
    public ResponseData<String> updateTerm(@RequestParam Long termId, @RequestBody TermRequestDTO requestDTO) {
        return termService.update(termId, requestDTO);
    }

    // 용어 삭제
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "용어 삭제")
    public Response deleteTerm(@RequestParam Long termId) {
        return termService.deleteTerm(termId);
    }

}
