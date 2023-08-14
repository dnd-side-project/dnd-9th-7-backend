package com.dnd.MusicLog.global.jwt.util;

import com.dnd.MusicLog.global.error.exception.BusinessLogicException;
import com.dnd.MusicLog.global.error.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
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

    public String generateToken(String subject, Date expiredAt, String tokenType) {

        Key key = checkTokenType(tokenType);

        return Jwts.builder()
            .setSubject(subject)
            .claim("tokenType", tokenType)
            .setExpiration(expiredAt)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();
    }

    public String extractAccessTokenSubject(String bearerToken) {

        if (bearerToken == null || !bearerToken.startsWith(JwtProperties.BEARER_TYPE)) {
            throw new BusinessLogicException(ErrorCode.INVALID_ACCESS_TOKEN);
        }

        String accessToken = bearerToken.replace(JwtProperties.BEARER_TYPE, "");

        Claims claims = parseClaims(accessToken, JwtProperties.ACCESS_TOKEN_TYPE);
        return claims.getSubject();

    }

    public String extractRefreshTokenSubject(String refreshToken) {
        Claims claims = parseClaims(refreshToken, JwtProperties.REFRESH_TOKEN_TYPE);
        return claims.getSubject();
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
            throw new BusinessLogicException(getErrorCode(tokenType, true));
        } catch (Exception e) {
            throw new BusinessLogicException(getErrorCode(tokenType, false));
        }
    }

    private ErrorCode getErrorCode(String tokenType, boolean isExpired) {
        if (isExpired) {
            return tokenType.equals(JwtProperties.ACCESS_TOKEN_TYPE) ? ErrorCode.EXPIRED_ACCESS_TOKEN : ErrorCode.EXPIRED_REFRESH_TOKEN;
        } else {
            return tokenType.equals(JwtProperties.ACCESS_TOKEN_TYPE) ? ErrorCode.INVALID_ACCESS_TOKEN : ErrorCode.INVALID_REFRESH_TOKEN;
        }
    }

    private Key checkTokenType(String tokenType) {
        if (tokenType.equals("access")) {
            return accessSecretKey;
        } else {
            return refreshSecretKey;
        }
    }
}
