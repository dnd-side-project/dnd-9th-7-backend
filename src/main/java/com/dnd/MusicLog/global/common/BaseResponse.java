package com.dnd.MusicLog.global.common;

import lombok.Builder;

@Builder
public record BaseResponse<T>(int status, String message, T data) {

}
