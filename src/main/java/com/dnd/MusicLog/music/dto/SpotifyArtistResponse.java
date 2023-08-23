package com.dnd.MusicLog.music.dto;

import java.util.List;

public record SpotifyArtistResponse(String id, String name, List<SpotifyImageResponse> images) {

}
