package com.dnd.MusicLog.global.jwt.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class AccessTokenResponseDto {

    private String accessToken;

}
