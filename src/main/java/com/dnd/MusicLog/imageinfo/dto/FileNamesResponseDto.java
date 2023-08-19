package com.dnd.MusicLog.imageinfo.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record FileNamesResponseDto(List<String> fileNames) {
}
