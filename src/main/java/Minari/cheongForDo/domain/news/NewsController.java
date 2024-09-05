package Minari.cheongForDo.domain.news;

import Minari.cheongForDo.domain.news.dto.CrawlingResult;
import Minari.cheongForDo.global.response.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/news")
public class NewsController {
    private final NewsService newsService;

    @GetMapping
    public ResponseEntity<ResponseData<List<CrawlingResult>>> getNews(@RequestParam String category) {
        return ResponseEntity.ok(ResponseData.of(HttpStatus.OK, "뉴스를 성공적으로 불러왔어요.", newsService.crawlWithCategory(category)));
    }
}
