package Minari.cheongForDo.domain.member.service;

import Minari.cheongForDo.domain.member.entity.MemberEntity;

import io.jsonwebtoken.*;
import lombok.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtTokenService {
    @Value("${jwtToken}")
    private String secret; // application.properties에 정의된 시크릿 키

    public Claims parseToken(String token) { // 토큰의 이름과 형식의 유효성 검사
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) { // 토큰이 만료되었을 경우
            throw new RuntimeException("토큰이 만료되었습니다.");
        } catch (JwtException e) { // 토큰이 유효하지 않을 경우
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }
    }

    public boolean isTokenExpired(String token){ // 토큰의 만료 여부 검사
        try{
            Claims claims = parseToken(token);
            return claims.getExpiration().before(new Date());
        }catch (ExpiredJwtException e) { // 토큰이 만료되었을 경우
            return true;
        } catch (JwtException ex) { // 토큰이 유효하지 않을 때
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }
    }
}


