package com.dnd.MusicLog.log.dto;

import java.util.List;

public record RandomMyPlaylistDto(String albumImageUrl, List<String> artists, String name) {
}
