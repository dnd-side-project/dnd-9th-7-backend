package com.dnd.MusicLog.global.jwt.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AccessTokenResponseDto {

    private String accessToken;

    public static AccessTokenResponseDto of(String accessToken) {
        return new AccessTokenResponseDto(accessToken);
    }
}
