package com.dnd.MusicLog.taginfo.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Time {

    아침("E9FFAC"),
    점심("CAED66"),
    저녁("1F489A"),
    새벽("3D33AA");

    private String color;
}
