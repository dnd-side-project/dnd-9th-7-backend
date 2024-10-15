package com.dnd.MusicLog.log.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Weather implements Category{

    SUNNY("맑은", "59FFCD"),
    CLOUDY("흐린", "5897AC"),
    RAIN("비오는", "128A92"),
    SNOW("눈오는", "ACF2E6");

    private String status;
    private String color;
}
