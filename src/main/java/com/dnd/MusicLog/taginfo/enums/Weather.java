package com.dnd.MusicLog.taginfo.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Weather {

    맑음("59FFCD"),
    흐림("5897AC"),
    비("128A92"),
    눈("ACF2E6");

    private String color;
}
