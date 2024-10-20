package Minari.cheongForDo.domain.oauth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OAuthLoginRequestDTO {
    private String id;  // 사용자가 입력하는 아이디
    private String email;  // 구글로부터 가져오는 이메일
}