package com.dnd.MusicLog.global.error.dto;

import lombok.Builder;

@Builder
public record ErrorResponse(int status, String message) {

}