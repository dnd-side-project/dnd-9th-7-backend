package com.dnd.MusicLog.taginfo.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Season {

    SPRING("BDFF89"),
    SUMMER("4290EC"),
    AUTUMN("FDF8DC"),
    WINTER("AEF2FF");

    private String color;
}
