package Minari.cheongForDo.domain.termary.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class TermaryService {

    private final RestTemplate restTemplate;
    private final String OPENAI_API_KEY = "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";
    private final String OPENAI_URL = "https://api.openai.com/v1/completions";

    public String summarizeTerm(String termExplain) {
        String prompt = "한국어로 이 설명을 청소년이 쉽게 이해할 수 있도록 요약해줘: " + termExplain;

        String response = callOpenAiApi(prompt);

        return response;
    }

    private String callOpenAiApi(String prompt) {
        // API 호출을 위한 설정
        // 예시: HTTP 요청 보내기
        // 필요에 따라 적절한 요청 메소드(GET/POST)를 사용하세요.

        // 요청 본문 및 헤더 구성
        String jsonResponse = ""; // 실제로 OpenAI API 호출 코드 작성

        // API 응답 처리
        return jsonResponse;
    }
}
