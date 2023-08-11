package com.dnd.MusicLog.global.jwt.dto;

import lombok.Builder;

@Builder
public record RefreshToken(String refreshToken) {

}
