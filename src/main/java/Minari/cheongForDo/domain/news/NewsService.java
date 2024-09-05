package Minari.cheongForDo.domain.news;

import Minari.cheongForDo.domain.news.dto.CrawlingResult;
import Minari.cheongForDo.domain.news.dto.NewsCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsCrawler crawler;

    public List<CrawlingResult> crawlWithCategory(String cat) {
        return crawler.crawl(NewsCategory.of(cat).getUrl());
    }
}
