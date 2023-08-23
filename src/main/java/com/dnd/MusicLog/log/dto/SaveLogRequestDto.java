package com.dnd.MusicLog.log.dto;

import com.dnd.MusicLog.music.enums.MusicType;
import com.dnd.MusicLog.log.enums.Feeling;
import com.dnd.MusicLog.log.enums.Season;
import com.dnd.MusicLog.log.enums.Time;
import com.dnd.MusicLog.log.enums.Weather;
import jakarta.annotation.Nullable;

import java.time.LocalDate;

public record SaveLogRequestDto(@Nullable String spotifyId, @Nullable String location, @Nullable String record,
                                @Nullable String review, @Nullable String videoId, @Nullable String title,
                                @Nullable String channelTitle, @Nullable String publishedAt, LocalDate date, boolean temp,
                                Feeling feeling, Time time, Weather weather, Season season,
                                String name, @Nullable String imageUrl, String artist, MusicType musicType) {
}
