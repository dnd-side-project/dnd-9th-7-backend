package com.dnd.MusicLog.global.jwt.service;

import com.dnd.MusicLog.global.jwt.util.JwtProperties;
import com.dnd.MusicLog.global.jwt.util.JwtTokenProvider;
import com.dnd.MusicLog.global.jwt.dto.AuthTokensResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class AuthTokensGeneratorService {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthTokensResponseDto generateAuthToken(Long userId) {

        long now = (new Date()).getTime();
        Date accessTokenExpiredAt = new Date(now + JwtProperties.ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpiredAt = new Date(now + JwtProperties.REFRESH_TOKEN_EXPIRE_TIME);

        String subject = userId.toString();
        String accessToken = jwtTokenProvider.generateToken(subject, accessTokenExpiredAt, JwtProperties.ACCESS_TOKEN_TYPE);
        String refreshToken = jwtTokenProvider.generateToken(subject, refreshTokenExpiredAt, JwtProperties.REFRESH_TOKEN_TYPE);

        return AuthTokensResponseDto.of(accessToken, refreshToken, JwtProperties.BEARER_TYPE,
            JwtProperties.ACCESS_TOKEN_EXPIRE_TIME / 1000L);
    }

}
