package com.dnd.MusicLog.taginfo.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Feeling {

    HAPPINESS("00C2FF"), // 행복
    EXCITEMENT("7744E4"), // 신남
    FLUTTER("DB75FF"), // 설렘
    SERENITY("4CBD87"), // 평온
    EMPTINESS("04002D"), // 공허
    DEPRESSION("35516C"), // 우울
    SADNESS("2D7ECA"), // 슬픔
    ANGER("AA465A"); // 분노

    private String color;
}
