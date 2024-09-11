package Minari.cheongForDo.domain.news.dto;

import Minari.cheongForDo.global.exception.CustomErrorCode;
import Minari.cheongForDo.global.exception.CustomException;
import lombok.Getter;

@Getter
public enum NewsCategory {
    MAIN("main", "https://news.naver.com/breakingnews/section/101/263"),
    SECURITY("securities", "https://news.naver.com/breakingnews/section/101/258"),
    FINANCE("finance", "https://news.naver.com/breakingnews/section/101/259"),
    ECONOMY("economy", "https://news.naver.com/breakingnews/section/101/263"),
    REAL_ESTATE("realEstate", "https://news.naver.com/breakingnews/section/101/260"),
    INDUSTRIAL_BUSINESS("industrialBusiness", "https://news.naver.com/breakingnews/section/101/261"),
    HOT_NEWS("hotNews", "https://news.naver.com/breakingnews/section/101"),
    RANKIN("ranking", "https://news.naver.com/main/ranking/popularDay.naver?mid=etc&sid1=111")
    ;
    private final String incomingName;
    private final String url;

    NewsCategory(String incomingName, String url) {
        this.incomingName = incomingName;
        this.url = url;
    }

    public static NewsCategory of(String category) {
        for (NewsCategory cat : values()) {
            if (cat.incomingName.equalsIgnoreCase(category) || cat.name().equalsIgnoreCase(category))
                return cat;
        }
        throw new CustomException(CustomErrorCode.INVALID_NEWS_CATEGORY);
    }
}
