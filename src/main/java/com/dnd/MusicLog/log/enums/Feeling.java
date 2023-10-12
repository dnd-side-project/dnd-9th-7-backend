package com.dnd.MusicLog.log.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Feeling implements Category{

    HAPPINESS("행복한","00C2FF"), // 행복
    EXCITEMENT("신나는","7744E4"), // 신남
    FLUTTER("설레는", "DB75FF"), // 설렘
    SERENITY("평온한", "4CBD87"), // 평온
    EMPTINESS("슬픈", "04002D"), // 공허
    DEPRESSION("우울한", "35516C"), // 우울
    SADNESS("공허한", "2D7ECA"), // 슬픔
    ANGER("분노한", "AA465A"); // 분노

    private String status;
    private String color;

}
