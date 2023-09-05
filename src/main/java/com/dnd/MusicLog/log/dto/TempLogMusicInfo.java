package com.dnd.MusicLog.log.dto;

import java.util.List;

public record TempLogMusicInfo(long logId, String albumImageUrl, String name, List<String> artists) {
}
