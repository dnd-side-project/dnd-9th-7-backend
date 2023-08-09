package com.dnd.MusicLog.global.jwt.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthTokensResponseDto {

    private String accessToken;
    private String refreshToken;
    private String grantType;
    private Long expiresIn;

    public static AuthTokensResponseDto of(String accessToken, String refreshToken, String grantType, Long expiresIn) {
        return new AuthTokensResponseDto(accessToken, refreshToken, grantType, expiresIn);
    }
}
