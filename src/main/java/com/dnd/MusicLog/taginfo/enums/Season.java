package com.dnd.MusicLog.taginfo.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Season {

    봄("BDFF89"),
    여름("4290EC"),
    가을("FDF8DC"),
    겨울("AEF2FF");

    private String color;
}
