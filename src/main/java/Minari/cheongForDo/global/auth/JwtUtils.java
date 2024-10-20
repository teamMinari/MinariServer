package Minari.cheongForDo.global.auth;

import Minari.cheongForDo.domain.member.entity.MemberEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
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
        this.secretKey = new SecretKeySpec(properties.getSecretKey().getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());
    }

    public String getEmail(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | SignatureException e) {
            return false;
        }
    }

    public boolean isExpired(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            // 토큰이 만료된 경우 예외가 발생하므로 true 반환
            return true;
        } catch (Exception e) {
            // 다른 예외의 경우도 만료된 것으로 간주
            return true;
        }
    }

    public String getUserEmailFromToken(String token) {
        return getEmail(token);
    }

    // JWT 토큰을 생성하는 메서드
    public JwtInfo generateToken(MemberEntity member) {
        long now = new Date().getTime();

        String accessToken = Jwts.builder()
                .claim("authority", member.getAuthority())
                .setSubject(member.getEmail())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + properties.getAccessExpired()))
                .signWith(secretKey)
                .compact();

        String refreshToken = Jwts.builder()
                .claim("authority", member.getAuthority())
                .setSubject(member.getEmail())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + properties.getRefreshExpired()))
                .signWith(secretKey)
                .compact();

        return JwtInfo.of(accessToken, refreshToken);
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
