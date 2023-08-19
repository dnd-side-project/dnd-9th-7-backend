package com.dnd.MusicLog.music.dto;

import java.util.List;

public record SearchCustomMusicResponseDto(int offset, int total, List<CustomMusicItem> items) {

}
