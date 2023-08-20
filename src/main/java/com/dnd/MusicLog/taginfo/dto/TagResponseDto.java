package com.dnd.MusicLog.taginfo.dto;

import com.dnd.MusicLog.taginfo.entity.TagInfo;
import lombok.Value;

@Value
public class TagResponseDto {

    String feeling;
    String time;
    String weather;
    String season;

    public TagResponseDto(TagInfo tagInfo) {
        this.feeling = String.valueOf(tagInfo.getFeeling());
        this.time = String.valueOf(tagInfo.getTime());
        this.weather = String.valueOf(tagInfo.getWeather());
        this.season = String.valueOf(tagInfo.getSeason());
    }
}
