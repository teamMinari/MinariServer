package Minari.cheongForDo.global.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor(staticName = "of")
public class JwtInfo {
    private final String accessToken;
    private final String refreshToken;
}