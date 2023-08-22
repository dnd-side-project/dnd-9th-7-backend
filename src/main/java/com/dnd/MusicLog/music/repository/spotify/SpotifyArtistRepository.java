package com.dnd.MusicLog.music.repository.spotify;

import com.dnd.MusicLog.music.entity.spotify.SpotifyArtist;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpotifyArtistRepository extends JpaRepository<SpotifyArtist, Long> {

    Optional<SpotifyArtist> findBySpotifyId(String spotifyId);
}
