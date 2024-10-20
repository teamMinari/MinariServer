package Minari.cheongForDo.domain.news.enums;

import Minari.cheongForDo.global.exception.CustomErrorCode;
import Minari.cheongForDo.global.exception.CustomException;
import lombok.Getter;

@Getter
public enum NewsCategory {
    SECURITY("securities", "https://news.naver.com/breakingnews/section/101/258"), // 증권
    FINANCE("finance", "https://news.naver.com/breakingnews/section/101/259"), // 금융
    ECONOMY("economy", "https://news.naver.com/breakingnews/section/101/263"), // 경제 일반
    REAL_ESTATE("realEstate", "https://news.naver.com/breakingnews/section/101/260"), // 부동산
    INDUSTRIAL_BUSINESS("industrialBusiness", "https://news.naver.com/breakingnews/section/101/261"), // 산업/재계
    HOT_NEWS("hotNews", "https://news.naver.com/section/101"),
//    RANKIN("ranking", "https://news.naver.com/main/ranking/popularDay.naver?mid=etc&sid1=111")
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
