package com.dnd.MusicLog.log.dto;

import com.dnd.MusicLog.log.enums.Feeling;
import com.dnd.MusicLog.log.enums.Time;
import com.dnd.MusicLog.log.enums.Weather;

import java.time.LocalDate;
import java.util.List;

public record GetLogMusicResponseDto(String imageUrl, List<String> artists, String name, String review, Feeling feeling, Time time,
                                     Weather weather, String location, LocalDate date) {

}
