package com.dnd.MusicLog.log.dto;

import java.time.LocalDate;

public record GetLogPlayResponseDto(String title, String channelTitle, LocalDate date, String youtubeId){
}
