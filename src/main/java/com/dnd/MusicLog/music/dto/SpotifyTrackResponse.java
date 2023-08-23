package com.dnd.MusicLog.music.dto;

import java.util.List;

public record SpotifyTrackResponse(int offset, int total, List<SpotifyItemResponse> items) {

}
