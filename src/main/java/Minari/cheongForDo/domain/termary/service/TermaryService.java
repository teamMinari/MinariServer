package Minari.cheongForDo.domain.termary.service;

import Minari.cheongForDo.domain.term.entity.Term;
import Minari.cheongForDo.domain.term.repository.TermRepository;
import Minari.cheongForDo.domain.termary.dto.TermaryRequestDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TermaryService {

    private final RestTemplate restTemplate;
    private final TermRepository termRepository;

    @Value("${OPENAI_API_KEY}")
    private String openaiApiKey;

    private final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";

    public TermaryService(RestTemplate restTemplate, TermRepository termRepository) {
        this.restTemplate = restTemplate;
        this.termRepository = termRepository;
    }

    public String summarizeTerm(TermaryRequestDTO termaryRequestDTO) {
        String termNm = termaryRequestDTO.getTermNm();

        Term term = termRepository.findByTermNm(termNm);
        if (term == null) {
            throw new IllegalArgumentException("해당 용어를 찾을 수 없습니다: " + termNm);
        }

        String termExplain = term.getTermExplain();
        String prompt = "너는 중학생, 고등학생에게 경제용어를 설명해주는 사람이야, 그러니까 청소년이 쉽게 이해할 수 있도록 용어의 내용을 요약해줘야 하고 정확해야 해. " +
                "무조건 존댓말을 사용해줘. 반말은 사용하지 마.최대한 자세하게 설명하면 좋을 것 같아. 하지만 너무 딱딱하게 말하는 것 보다는 부드럽게 말해주면 좋겠어.: " + termExplain;

        return callOpenAiApi(prompt);
    }

    private String callOpenAiApi(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + openaiApiKey);
        headers.set("Content-Type", "application/json");

        String requestBody = String.format(
                "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}], \"max_tokens\": 400}",
                prompt.replace("\"", "\\\"")
        );

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    OPENAI_URL,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            System.out.println("Response Status Code: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode choices = root.path("choices");
            if (choices.isArray() && choices.size() > 0) {
                return choices.get(0).path("message").path("content").asText().trim();
            } else {
                return "유효한 응답을 받지 못했습니다.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "요청 처리 중 오류가 발생했습니다.";
        } finally {
            try {
                Thread.sleep(1500); // 1.5초 대기
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("스레드 인터럽트 발생: " + e.getMessage());
            }
        }
    }
}