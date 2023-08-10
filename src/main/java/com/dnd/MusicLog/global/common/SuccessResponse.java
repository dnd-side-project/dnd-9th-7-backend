package com.dnd.MusicLog.global.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
public record SuccessResponse(int status, String message) {
}
