package Minari.cheongForDo.domain.member.authority;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "admin")
public class AdminProperties {
    private String id;
    private String password;
    private String email;
    private String authority;
}