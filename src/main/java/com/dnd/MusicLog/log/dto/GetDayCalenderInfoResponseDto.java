package com.dnd.MusicLog.log.dto;

import java.util.List;

public record GetDayCalenderInfoResponseDto(long logId, String albumImageUrl, List<String> artists, String name, boolean representation) {
}
