package com.dnd.MusicLog.user.controller;

import com.dnd.MusicLog.user.dto.AuthTokensResponseDto;
import com.dnd.MusicLog.user.oauth.KakaoLoginParams;
import com.dnd.MusicLog.user.service.OAuthLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class OAuthController {
    private final OAuthLoginService oAuthLoginService;

    @PostMapping("/kakao")
    public ResponseEntity<AuthTokensResponseDto> loginUser(@RequestBody KakaoLoginParams params) {
        return ResponseEntity.ok(oAuthLoginService.loginUser(params));
    }
}
