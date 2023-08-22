package com.dnd.MusicLog.music.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record SpotifyAlbumResponse(String id,
                                   String name,
                                   String releaseDate,
                                   List<SpotifyImageResponse> images) {

    public SpotifyAlbumResponse(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name,
        @JsonProperty("release_date") String releaseDate,
        @JsonProperty("images") List<SpotifyImageResponse> images) {
        this.id = id;
        this.name = name;
        this.releaseDate = releaseDate;
        this.images = images;
    }
}
