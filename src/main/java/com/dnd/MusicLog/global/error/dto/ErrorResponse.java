package com.dnd.MusicLog.global.error.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public record ErrorResponse(int status, String message) {

}