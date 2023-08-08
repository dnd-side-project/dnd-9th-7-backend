package com.dnd.MusicLog.global.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public record BaseResponse<T>(int status, String message, T data) {

}
