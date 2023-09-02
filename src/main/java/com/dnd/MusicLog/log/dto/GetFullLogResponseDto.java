package com.dnd.MusicLog.log.dto;

public record GetFullLogResponseDto(GetLogMusicResponseDto musicResponseDto, GetLogRecordResponseDto recordResponseDto,
                                    GetLogPlayResponseDto playResponseDto) {
}
