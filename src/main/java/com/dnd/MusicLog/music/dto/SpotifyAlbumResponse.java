package com.dnd.MusicLog.music.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record SpotifyAlbumResponse(String id,
                                   String name,
                                   int totalTracks,
                                   String releaseDate,
                                   String releaseDatePrecision,
                                   List<SpotifyImageResponse> images) {

    public SpotifyAlbumResponse(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name,
        @JsonProperty("total_tracks") int totalTracks,
        @JsonProperty("release_date") String releaseDate,
        @JsonProperty("release_date_precision") String releaseDatePrecision,
        @JsonProperty("images") List<SpotifyImageResponse> images) {
        this.id = id;
        this.name = name;
        this.totalTracks = totalTracks;
        this.releaseDate = releaseDate;
        this.releaseDatePrecision = releaseDatePrecision;
        this.images = images;
    }
}
