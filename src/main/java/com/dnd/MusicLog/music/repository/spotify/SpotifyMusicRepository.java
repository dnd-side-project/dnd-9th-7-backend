package com.dnd.MusicLog.music.repository.spotify;

import com.dnd.MusicLog.music.entity.spotify.SpotifyMusic;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpotifyMusicRepository extends JpaRepository<SpotifyMusic, Long> {

    Optional<SpotifyMusic> findBySpotifyId(String spotifyId);
}
