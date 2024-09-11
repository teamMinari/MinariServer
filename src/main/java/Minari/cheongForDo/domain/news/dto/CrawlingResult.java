package Minari.cheongForDo.domain.news.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class CrawlingResult {
    private final String title;
    private final String url;
    private final String company;
    private final String thumbnail;
    private final String uploadTime;
}
