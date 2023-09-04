package com.dnd.MusicLog.log.dto;

import java.time.LocalDate;
import java.util.List;

public record GetLogMusicResponseDto(String albumImageUrl, List<String> artists, String name, String review, String feeling, String time,
                                     String weather, String location, LocalDate date) {

}
