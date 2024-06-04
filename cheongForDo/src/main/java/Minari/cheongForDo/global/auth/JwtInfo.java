package Minari.cheongForDo.global.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor(staticName = "of")
public class JwtInfo {
    private final String accessToken;
    private final String refreshToken;
}