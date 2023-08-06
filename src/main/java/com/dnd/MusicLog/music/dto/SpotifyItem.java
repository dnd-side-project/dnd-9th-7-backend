package com.dnd.MusicLog.music.dto;

public record SpotifyItem(String id, String name, SpotifyAlbum album, SpotifyArtist[] artists) {

}
