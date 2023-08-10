package com.dnd.MusicLog.global.jwt.service;

import com.dnd.MusicLog.global.jwt.dto.AccessTokenResponseDto;
import com.dnd.MusicLog.global.jwt.util.JwtProperties;
import com.dnd.MusicLog.global.jwt.util.JwtTokenProvider;
import com.dnd.MusicLog.user.entity.User;
import com.dnd.MusicLog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AccessTokenResponseDto regenerateAccessToken(String refreshToken, String tokenType) {

        String accessToken = null;
        long now = (new Date()).getTime();
        Date accessTokenExpiredAt = new Date(now + JwtProperties.ACCESS_TOKEN_EXPIRE_TIME);
        String userId = jwtTokenProvider.extractSubject(refreshToken, tokenType);
        Optional<User> optionalUser = userRepository.findById(Long.valueOf(userId));
        if (optionalUser.isEmpty()) {
            log.error("유저 리소스가 없습니다.");
        } else {
            accessToken = jwtTokenProvider.generateToken(userId, accessTokenExpiredAt, JwtProperties.ACCESS_TOKEN_TYPE);
        }

        return AccessTokenResponseDto.of(accessToken);

    }
}
