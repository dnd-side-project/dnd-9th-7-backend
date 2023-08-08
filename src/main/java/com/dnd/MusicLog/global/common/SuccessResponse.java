package com.dnd.MusicLog.global.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public record SuccessResponse(int status, String message) {
}
