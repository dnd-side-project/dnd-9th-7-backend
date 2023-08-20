package com.dnd.MusicLog.taginfo.dto;

import com.dnd.MusicLog.taginfo.enums.Feeling;
import com.dnd.MusicLog.taginfo.enums.Season;
import com.dnd.MusicLog.taginfo.enums.Time;
import com.dnd.MusicLog.taginfo.enums.Weather;

public record Tag(Feeling feeling, Time time, Weather weather, Season season) {
}
