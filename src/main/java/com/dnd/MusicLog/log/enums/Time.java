package com.dnd.MusicLog.log.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Time implements Category{

    MORNING("아침", "E9FFAC"),
    LUNCH("점심", "CAED66"),
    DINNER("저녁", "1F489A"),
    DAWN("새벽", "3D33AA");

    private String status;
    private String color;
}
