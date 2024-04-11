package Minari.cheongForDo.domain.member.service;

import Minari.cheongForDo.domain.member.entity.MemberEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService {
    @Value("${//뭔가 들어들어오겠지//}")
    private String secret; // application.properties에 정의된 시크릿 키

    public Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

}

