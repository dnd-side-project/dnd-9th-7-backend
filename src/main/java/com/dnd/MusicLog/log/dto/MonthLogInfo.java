package com.dnd.MusicLog.log.dto;

import java.util.List;

public record MonthLogInfo(int year, int month, long dayCount, long recordCount,
                           List<CalenderAlbumImageInfo> calenderAlbumImageInfoList) {
}
