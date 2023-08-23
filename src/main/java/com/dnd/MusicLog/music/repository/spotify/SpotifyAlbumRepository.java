package com.dnd.MusicLog.music.repository.spotify;

import com.dnd.MusicLog.music.entity.spotify.SpotifyAlbum;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotifyAlbumRepository extends JpaRepository<SpotifyAlbum, Long> {

    Optional<SpotifyAlbum> findBySpotifyId(String spotifyId);
}
