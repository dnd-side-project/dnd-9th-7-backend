package com.dnd.MusicLog.taginfo.dto;

import com.dnd.MusicLog.taginfo.entity.TagInfo;
import com.dnd.MusicLog.log.enums.Feeling;
import com.dnd.MusicLog.log.enums.Season;
import com.dnd.MusicLog.log.enums.Time;
import com.dnd.MusicLog.log.enums.Weather;
import lombok.Value;

@Value
public class TagResponseDto {

    Feeling feeling;
    Time time;
    Weather weather;
    Season season;

    public TagResponseDto(TagInfo tagInfo) {
        this.feeling = tagInfo.getFeeling();
        this.time = tagInfo.getTime();
        this.weather = tagInfo.getWeather();
        this.season = tagInfo.getSeason();
    }
}
