package com.dnd.MusicLog.log.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetCategoryStatusDto {
    private boolean HAPPINESS = false;
    private boolean EXCITEMENT = false;
    private boolean FLUTTER = false;
    private boolean SERENITY = false;
    private boolean EMPTINESS = false;
    private boolean DEPRESSION = false;
    private boolean SADNESS = false;
    private boolean ANGER = false;
    private boolean MORNING = false;
    private boolean LUNCH = false;
    private boolean DINNER = false;
    private boolean DAWN = false;
    private boolean SUNNY = false;
    private boolean CLOUDY = false;
    private boolean RAIN = false;
    private boolean SNOW = false;
    private boolean SPRING = false;
    private boolean SUMMER = false;
    private boolean AUTUMN = false;
    private boolean WINTER = false;
}
