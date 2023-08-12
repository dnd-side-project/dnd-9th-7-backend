package com.dnd.MusicLog.music.dto;

public record SaveMusicRequestDto(MusicRequestDto music, ArtistRequestDto[] artists, AlbumRequestDto albums) {

    public record MusicRequestDto(String name, String imageUrl, String uniqueId, boolean custom, String releaseDate) {

    }

    public record ArtistRequestDto(String name, String imageUrl, String uniqueId, boolean custom) {

    }

    public record AlbumRequestDto(String name, String imageUrl, String uniqueId, boolean custom, String releaseDate) {

    }
}
