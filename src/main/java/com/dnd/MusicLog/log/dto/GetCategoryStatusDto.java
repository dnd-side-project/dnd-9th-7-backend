package com.dnd.MusicLog.log.dto;

public record GetCategoryStatusDto(GetFeelingStatusDto feelingStatus, GetTimeStatusDto timeStatus,
                                   GetWeatherStatusDto weatherStatus, GetSeasonStatusDto seasonStatus) {
}
