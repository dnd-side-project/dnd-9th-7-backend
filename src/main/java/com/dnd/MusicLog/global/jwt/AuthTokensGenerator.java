package com.dnd.MusicLog.global.jwt;

import com.dnd.MusicLog.user.dto.AuthTokensResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class AuthTokensGenerator {
    private static final String BEARER_TYPE = "Bearer";
    private static final String ACCESS_TOKEN_TYPE = "access";
    private static final String REFRESH_TOKEN_TYPE = "refresh";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;            // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일

    private final JwtTokenProvider jwtTokenProvider;

    public AuthTokensResponseDto generate(Long userId) {
        long now = (new Date()).getTime();
        Date accessTokenExpiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpiredAt = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

        String subject = userId.toString();
        String accessToken = jwtTokenProvider.generate(subject, accessTokenExpiredAt, ACCESS_TOKEN_TYPE);
        String refreshToken = jwtTokenProvider.generate(subject, refreshTokenExpiredAt, REFRESH_TOKEN_TYPE);

        return AuthTokensResponseDto.of(accessToken, refreshToken, BEARER_TYPE, ACCESS_TOKEN_EXPIRE_TIME / 1000L);
    }

    public Long extractUserId(String token, String tokenType) {
        return Long.valueOf(jwtTokenProvider.extractSubject(token, tokenType));
    }

    public String extractTokenType(String token, String tokenType) {
        return String.valueOf(jwtTokenProvider.extractTokenType(token, tokenType));
    }
}
