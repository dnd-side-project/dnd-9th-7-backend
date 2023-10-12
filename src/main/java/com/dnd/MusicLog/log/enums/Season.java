package com.dnd.MusicLog.log.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Season implements Category{

    SPRING("봄", "BDFF89"),
    SUMMER("여름", "4290EC"),
    AUTUMN("가을", "FDF8DC"),
    WINTER("겨울", "AEF2FF");

    private String status;
    private String color;
}
