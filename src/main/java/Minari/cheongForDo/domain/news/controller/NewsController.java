package Minari.cheongForDo.domain.news.controller;

import Minari.cheongForDo.domain.news.service.NewsService;
import Minari.cheongForDo.domain.news.dto.CrawlingResult;
import Minari.cheongForDo.global.response.ResponseData;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/news")
@Tag(name = "NEWS", description = "news API")
public class NewsController {
    private final NewsService newsService;

    @GetMapping
    public ResponseEntity<ResponseData<List<CrawlingResult>>> getNews(@RequestParam String category) {
        return ResponseEntity.ok(ResponseData.of(HttpStatus.OK, "뉴스를 성공적으로 불러왔습니다.", newsService.crawlWithCategory(category)));
    }
}
