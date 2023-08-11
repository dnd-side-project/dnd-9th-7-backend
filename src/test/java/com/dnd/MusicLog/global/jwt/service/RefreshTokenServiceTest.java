package com.dnd.MusicLog.global.jwt.service;

import com.dnd.MusicLog.global.error.exception.BusinessLogicException;
import com.dnd.MusicLog.global.jwt.dto.AccessTokenResponseDto;
import com.dnd.MusicLog.global.jwt.util.JwtProperties;
import com.dnd.MusicLog.global.jwt.util.JwtTokenProvider;
import com.dnd.MusicLog.user.entity.User;
import com.dnd.MusicLog.user.enums.OAuthType;
import com.dnd.MusicLog.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@SpringBootTest
class RefreshTokenServiceTest {

    private final RefreshTokenService refreshTokenService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Autowired
    public RefreshTokenServiceTest(RefreshTokenService refreshTokenService, JwtTokenProvider jwtTokenProvider,
                                   UserRepository userRepository) {
        this.refreshTokenService = refreshTokenService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    @Test
    @Transactional
    @DisplayName("AccessToken 재발급 성공 테스트")
    void refreshTokenSuccessTest() {

        long now = (new Date()).getTime();
        Date refreshTokenExpiredAt = new Date(now + JwtProperties.REFRESH_TOKEN_EXPIRE_TIME);

        User user = User.builder()
            .oauthId("1234")
            .email("asdf@naver.com")
            .nickname("홍길동")
            .profileUrl("asdfsdfwef")
            .oauthType(OAuthType.KAKAO)
            .build();

        long userId = userRepository.save(user).getId();

        String refreshToken = jwtTokenProvider.generateToken(String.valueOf(userId), refreshTokenExpiredAt,
            JwtProperties.REFRESH_TOKEN_TYPE);

        AccessTokenResponseDto accessTokenResponseDto = refreshTokenService.regenerateAccessToken(refreshToken);

        org.assertj.core.api.Assertions.assertThat(accessTokenResponseDto.getAccessToken()).isNotNull();
    }

    @Test
    @Transactional
    @DisplayName("AccessToken 재발급 실패 테스트")
    void refreshTokenFailTest() {

        long now = (new Date()).getTime();
        Date refreshTokenExpiredAt = new Date(now + JwtProperties.REFRESH_TOKEN_EXPIRE_TIME);

        User user = User.builder()
            .oauthId("1234")
            .email("asdf@naver.com")
            .nickname("홍길동")
            .profileUrl("asdfsdfwef")
            .oauthType(OAuthType.KAKAO)
            .build();

        long userId = userRepository.save(user).getId();

        String refreshToken = jwtTokenProvider.generateToken("123", refreshTokenExpiredAt,
            JwtProperties.REFRESH_TOKEN_TYPE);

        Assertions.assertThrows(BusinessLogicException.class, () -> {
            refreshTokenService.regenerateAccessToken(refreshToken);
        });
    }
}