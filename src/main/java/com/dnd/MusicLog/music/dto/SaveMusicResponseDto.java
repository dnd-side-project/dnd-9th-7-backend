package com.dnd.MusicLog.music.dto;

import com.dnd.MusicLog.music.entity.Album;
import com.dnd.MusicLog.music.entity.Artist;
import com.dnd.MusicLog.music.entity.Music;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
public class SaveMusicResponseDto {

    MusicResponse music;
    List<ArtistResponse> artists = new ArrayList<>();
    AlbumResponse album;

    @Builder
    public SaveMusicResponseDto(Music music, List<Artist> artists, Album album) {
        this.music = new MusicResponse(
            music.getId(),
            music.getName(),
            music.getImageUrl(),
            music.getUniqueId(),
            music.isCustom(),
            music.getReleaseDate());
        if (artists != null) {
            List<ArtistResponse> artistResponses = artists.stream()
                .map(artist -> new ArtistResponse(
                    artist.getId(),
                    artist.getImageUrl(),
                    artist.getUniqueId(),
                    artist.isCustom()))
                .toList();
            this.artists.addAll(artistResponses);
        }
        this.album = album == null
            ? null
            : new AlbumResponse(
                album.getId(),
                album.getImageUrl(),
                album.getUniqueId(),
                album.isCustom(),
                album.getReleaseDate());
    }

    public record MusicResponse(
        long id,
        String name,
        String imageUrl,
        String uniqueId,
        boolean custom,
        String releaseDate) {

    }

    public record ArtistResponse(long id, String imageUrl, String uniqueId, boolean custom) {

    }

    public record AlbumResponse(long id, String imageUrl, String uniqueId, boolean custom, String releaseDate) {

    }
}
