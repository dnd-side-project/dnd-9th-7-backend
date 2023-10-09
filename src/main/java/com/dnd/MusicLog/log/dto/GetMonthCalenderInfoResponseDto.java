package com.dnd.MusicLog.log.dto;

public record GetMonthCalenderInfoResponseDto(MonthLogInfo lastMonthLogInfo, MonthLogInfo thisMonthLogInfo,
                                              MonthLogInfo nextMonthLogInfo) {
}
