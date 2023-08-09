package com.dnd.MusicLog.global.jwt.service;

import com.dnd.MusicLog.global.jwt.util.JwtTokenProvider;
import com.dnd.MusicLog.global.jwt.dto.AuthTokensResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.dnd.MusicLog.global.jwt.util.JwtProperties.*;

@Component
@RequiredArgsConstructor
public class AuthTokensGeneratorService {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthTokensResponseDto generateAuthToken(Long userId) {
        long now = (new Date()).getTime();
        Date accessTokenExpiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpiredAt = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

        String subject = userId.toString();
        String accessToken = jwtTokenProvider.generate(subject, accessTokenExpiredAt, ACCESS_TOKEN_TYPE);
        String refreshToken = jwtTokenProvider.generate(subject, refreshTokenExpiredAt, REFRESH_TOKEN_TYPE);

        return AuthTokensResponseDto.of(accessToken, refreshToken, BEARER_TYPE, ACCESS_TOKEN_EXPIRE_TIME / 1000L);
    }

//    public String extractTokenType(String token, String tokenType) {
//        return String.valueOf(jwtTokenProvider.extractTokenType(token, tokenType));
//    }
}
