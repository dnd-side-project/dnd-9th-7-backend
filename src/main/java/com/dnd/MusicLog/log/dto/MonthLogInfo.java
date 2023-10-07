package com.dnd.MusicLog.log.dto;

import java.util.List;

public record MonthLogInfo(long dayCount, long recordCount, List<CalenderAlbumImageInfo> calenderAlbumImageInfoList) {
}
