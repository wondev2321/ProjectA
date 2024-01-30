package com.nndev.projecta.auth.security.jwt;

import com.nndev.projecta.member.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class JwtTokenProvider {
    @Value("${jwt.secret-key.access-token}")
    private String accessSecretKey;

   /* @Value("${jwt.secret-key.refresh-token}")
    private String refreshSecretKey;*/

    @Value("${jwt.token-expired-time.access-token}")
    private Long accessTokenExpiredTimeMs;

    /*@Value("${jwt.token-expired-time.refresh-token}")
    private Long refreshTokenExpiredTimeMs;*/
    public String generateToken(Member member, TokenType type){

        String secretKey = null;
        Long expiredTime = null;

        //if (type.equals(TokenType.ACCESS)) {
            secretKey = accessSecretKey;
            expiredTime = accessTokenExpiredTimeMs;
        /*} else {
            secretKey = refreshSecretKey;
            expiredTime = refreshTokenExpiredTimeMs;
        }*/

        return Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(member))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredTime))
                .signWith(getKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractClaims(String token, TokenType type) {
        //if (type.equals(TokenType.ACCESS)) {
            return Jwts.parserBuilder().setSigningKey(getKey(accessSecretKey))
                    .build().parseClaimsJws(token).getBody();
       /*
        }
        return Jwts.parserBuilder().setSigningKey(getKey(refreshSecretKey))
                .build().parseClaimsJws(token).getBody();*/
    }

    /**
     * 토큰 속 정보 name 추출
     */
    public String getName(String token , TokenType type) {
        return extractClaims(token, type).get("name", String.class);
    }

    /**
     * 토큰 속 정보 email 추출
     */
    public String getEmail(String token, TokenType type) {
        return extractClaims(token, type).get("email", String.class);
    }

    private Key getKey(String key) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Map<String, Object> createHeader(){
        Map<String, Object> header = new HashMap<>();
        header.put("typ","JWT");
        header.put("alg","HS256"); // 해시 256 암호화
        return header;
    }

    private Map<String, Object> createClaims(Member member) { // payload
        Map<String, Object> claims = new HashMap<>();
        claims.put("id",member.getName());
        claims.put("email",member.getEmail());
        return claims;
    }

}
