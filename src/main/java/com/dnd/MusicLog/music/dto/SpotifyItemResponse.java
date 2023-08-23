package com.dnd.MusicLog.music.dto;

import java.util.List;

public record SpotifyItemResponse(String id,
                                  String name,
                                  SpotifyAlbumResponse album,
                                  List<SpotifyArtistResponse> artists) {

}
