package com.dnd.MusicLog.taginfo.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Feeling {

    행복("00C2FF"),
    신남("7744E4"),
    설렘("DB75FF"),
    평온("4CBD87"),
    공허("04002D"),
    우울("35516C"),
    슬픔("2D7ECA"),
    분노("AA465A");

    private String color;
}
