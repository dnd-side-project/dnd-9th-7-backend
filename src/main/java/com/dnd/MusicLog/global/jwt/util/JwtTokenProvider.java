package com.dnd.MusicLog.global.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final Key accessSecretKey;
    private final Key refreshSecretKey;

    public JwtTokenProvider(@Value("${jwt.access-secret-key}") String accessSecretKey,
                            @Value("${jwt.refresh-secret-key}") String refreshSecretKey) {
        byte[] accessKeyBytes = Decoders.BASE64.decode(accessSecretKey);
        byte[] refreshKeyBytes = Decoders.BASE64.decode(refreshSecretKey);
        this.accessSecretKey = Keys.hmacShaKeyFor(accessKeyBytes);
        this.refreshSecretKey = Keys.hmacShaKeyFor(refreshKeyBytes);
    }

    public String generate(String subject, Date expiredAt, String tokenType) {

        Key key = checkTokenType(tokenType);

        return Jwts.builder()
            .setSubject(subject)
            .claim("tokenType", tokenType)
            .setExpiration(expiredAt)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();
    }

    public String extractSubject(String token, String tokenType) {
        Claims claims = parseClaims(token, tokenType);
        return claims.getSubject();
    }

    public String extractTokenType(String token, String tokenType) {
        Claims claims = parseClaims(token, tokenType);
        return claims.get("tokenType", String.class);
    }

    private Claims parseClaims(String token, String tokenType) {

        Key key = checkTokenType(tokenType);

        try {
            return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    private Key checkTokenType(String tokenType) {
        if (tokenType == "access") {
            return accessSecretKey;
        } else {
            return refreshSecretKey;
        }
    }
}