package com.dnd.MusicLog.user.controller;

import com.dnd.MusicLog.global.common.BaseController;
import com.dnd.MusicLog.global.common.BaseResponse;
import com.dnd.MusicLog.global.jwt.dto.AccessTokenResponseDto;
import com.dnd.MusicLog.global.jwt.dto.AuthTokensResponseDto;
import com.dnd.MusicLog.global.jwt.dto.RefreshToken;
import com.dnd.MusicLog.global.jwt.service.RefreshTokenService;
import com.dnd.MusicLog.global.jwt.util.JwtProperties;
import com.dnd.MusicLog.user.oauth.KakaoLoginParams;
import com.dnd.MusicLog.user.service.OAuthLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class OAuthController extends BaseController {
    private final OAuthLoginService oAuthLoginService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/kakao")
    public ResponseEntity<BaseResponse<AuthTokensResponseDto>> loginUser(@RequestBody KakaoLoginParams params) {

        AuthTokensResponseDto responseDto = oAuthLoginService.loginUser(params);
        return createResponseEntity(HttpStatus.OK, "로그인 완료", responseDto);

    }

    @PostMapping("/token")
    public ResponseEntity<BaseResponse<AccessTokenResponseDto>> regenerateAccessToken(@RequestBody RefreshToken refreshToken) {

        AccessTokenResponseDto responseDto = refreshTokenService.regenerateAccessToken(refreshToken.refreshToken(),
            JwtProperties.REFRESH_TOKEN_TYPE);
        return createResponseEntity(HttpStatus.OK, "액세스 토큰 재발급 완료", responseDto);

    }
}
