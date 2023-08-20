package com.dnd.MusicLog.taginfo.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Weather {

    SUNNY("59FFCD"),
    CLOUDY("5897AC"),
    RAIN("128A92"),
    SNOW("ACF2E6");

    private String color;
}
