package Minari.cheongForDo.domain.news.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;

@Configuration
public class NewsConfig {
    @Bean
    public HttpClient client() {
        return HttpClient.newBuilder().build();
    }
}
