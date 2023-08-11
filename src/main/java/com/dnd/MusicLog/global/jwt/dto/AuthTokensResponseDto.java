package com.dnd.MusicLog.global.jwt.dto;

import lombok.*;

@Builder
public record AuthTokensResponseDto(String accessToken, String refreshToken, String grantType, Long expiresIn) {

}
