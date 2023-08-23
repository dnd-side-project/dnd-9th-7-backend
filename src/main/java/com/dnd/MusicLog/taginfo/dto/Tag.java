package com.dnd.MusicLog.taginfo.dto;

import com.dnd.MusicLog.log.enums.Feeling;
import com.dnd.MusicLog.log.enums.Season;
import com.dnd.MusicLog.log.enums.Time;
import com.dnd.MusicLog.log.enums.Weather;

public record Tag(Feeling feeling, Time time, Weather weather, Season season) {
}
