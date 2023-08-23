package com.dnd.MusicLog.log.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Time {

    MORNING("E9FFAC"),
    LUNCH("CAED66"),
    DINNER("1F489A"),
    DAWN("3D33AA");

    private String color;
}
