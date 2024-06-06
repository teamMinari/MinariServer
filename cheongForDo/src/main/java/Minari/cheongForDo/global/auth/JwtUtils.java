package Minari.cheongForDo.global.auth;

import Minari.cheongForDo.domain.member.entity.MemberEntity;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-expiration}")
    private long accessExpiration;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    private final UserDetailsService customUserDetailsService;

    private SecretKey key;

    @PostConstruct
    protected void init() {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String getEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isExpired(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return false;
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    public JwtInfo generateToken(MemberEntity member) {
        long now = (new Date()).getTime();

        String accessToken = Jwts.builder()
                .setSubject(member.getEmail())
                .claim("authority", member.getAuthority().name())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + accessExpiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(member.getEmail())
                .claim("authority", member.getAuthority().name())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + refreshExpiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return JwtInfo.of(accessToken, refreshToken);
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        String email = getEmail(token);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}