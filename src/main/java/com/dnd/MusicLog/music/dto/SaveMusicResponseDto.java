package com.dnd.MusicLog.music.dto;

import com.dnd.MusicLog.music.entity.Album;
import com.dnd.MusicLog.music.entity.Artist;
import com.dnd.MusicLog.music.entity.Music;
import java.util.Arrays;
import lombok.Builder;
import lombok.Value;

@Value
public class SaveMusicResponseDto {

    MusicResponse music;
    ArtistResponse[] artists;
    AlbumResponse album;

    @Builder
    public SaveMusicResponseDto(Music music, Artist[] artists, Album album) {
        this.music = new MusicResponse(
            music.getId(),
            music.getName(),
            music.getImageUrl(),
            music.getUniqueId(),
            music.isCustom(),
            music.getReleaseDate());
        this.artists = Arrays.stream(artists)
            .map(artist -> new ArtistResponse(
                artist.getId(),
                artist.getImageUrl(),
                artist.getUniqueId(),
                artist.isCustom()))
            .toArray(ArtistResponse[]::new);
        this.album = new AlbumResponse(
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
