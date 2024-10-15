package com.dnd.MusicLog.log.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetWeatherStatusDto {

    private boolean SUNNY = false;
    private boolean CLOUDY = false;
    private boolean RAIN = false;
    private boolean SNOW = false;

}
