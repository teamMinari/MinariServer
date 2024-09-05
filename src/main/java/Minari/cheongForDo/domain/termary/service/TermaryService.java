package Minari.cheongForDo.domain.termary.service;

import Minari.cheongForDo.domain.termary.dto.TermaryRequestDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TermaryService {

    private final RestTemplate restTemplate;
    private final String OPENAI_API_KEY = "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";
    private final String OPENAI_URL = "https://api.openai.com/v1/completions";

    @Autowired
    public TermaryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String summarizeTerm(TermaryRequestDTO termaryRequestDTO) {
        String termExplain = termaryRequestDTO.getTermExplain();
        String prompt = "이 설명을 청소년이 이해할 수 있도록 요약해 주세요: " + termExplain;
        return callOpenAiApi(prompt);
    }

    private String callOpenAiApi(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + OPENAI_API_KEY);
        headers.set("Content-Type", "application/json");

        String requestBody = "{\"model\": \"text-davinci-003\", \"prompt\": \"" + prompt + "\", \"max_tokens\": 100}";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                OPENAI_URL,
                HttpMethod.POST,
                entity,
                String.class
        );

        ObjectMapper mapper = new ObjectMapper();
        String result = "";
        try {
            JsonNode root = mapper.readTree(response.getBody());
            result = root.path("choices").get(0).path("text").asText();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.trim();
    }
}
