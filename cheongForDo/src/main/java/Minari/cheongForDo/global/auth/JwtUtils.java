package Minari.cheongForDo.global.auth;

import Minari.cheongForDo.domain.member.entity.MemberEntity;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtils {

    private final JwtProperties properties;
    private final SecretKey secretKey;
    private final UserDetailsService userDetailsService;

    public JwtUtils(JwtProperties properties, UserDetailsService userDetailsService) {
        this.properties = properties;
        this.userDetailsService = userDetailsService;
        this.secretKey = new SecretKeySpec(properties.getSecretKey().getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String getEmail(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public boolean isExpired(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return false;
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    public JwtInfo generateToken(MemberEntity member) {
        long now = new Date().getTime();

        String accessToken = Jwts.builder()
                .claim("authority", member.getAuthority())
                .subject(member.getEmail())
                .issuedAt(new Date(now))
                .expiration(new Date(now + properties.getAccessExpired()))
                .signWith(secretKey)
                .compact();

        String refreshToken = Jwts.builder()
                .claim("email", member.getEmail())
                .claim("authority", member.getAuthority())
                .subject(member.getEmail())
                .issuedAt(new Date(now))
                .expiration(new Date(now + properties.getRefreshExpired()))
                .signWith(secretKey)
                .compact();

        return JwtInfo.of(accessToken, refreshToken);
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getEmail(token));

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}