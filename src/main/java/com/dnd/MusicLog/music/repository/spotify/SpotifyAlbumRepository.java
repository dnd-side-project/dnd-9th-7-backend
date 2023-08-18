package com.dnd.MusicLog.music.repository.spotify;

import com.dnd.MusicLog.music.entity.spotify.SpotifyAlbum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpotifyAlbumRepository extends JpaRepository<SpotifyAlbum, Long> {

}
