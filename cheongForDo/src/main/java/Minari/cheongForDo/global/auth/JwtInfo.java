package Minari.cheongForDo.global.auth;

import lombok.Getter;

@Getter
public class JwtInfo {

    private final String accessToken;
    private final String refreshToken;

    private JwtInfo(
            String accessToken,
            String refreshToken
    ) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static JwtInfo of(
            String accessToken,
            String refreshToken
    ) {
        return new JwtInfo(accessToken, refreshToken);
    }

}