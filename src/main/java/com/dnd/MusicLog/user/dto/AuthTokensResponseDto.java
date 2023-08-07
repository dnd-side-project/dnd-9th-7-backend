package com.dnd.MusicLog.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthTokensResponseDto {
    private String accessToken;
    private String refreshToken;
    private String grantType;
    private Long expiresIn;

    public static AuthTokensResponseDto of(String accessToken, String refreshToken, String grantType, Long expiresIn) {
        return new AuthTokensResponseDto(accessToken, refreshToken, grantType, expiresIn);
    }
}
