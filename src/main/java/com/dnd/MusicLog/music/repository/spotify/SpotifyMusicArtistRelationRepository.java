package com.dnd.MusicLog.music.repository.spotify;

import com.dnd.MusicLog.music.entity.spotify.SpotifyMusicArtistRelation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpotifyMusicArtistRelationRepository extends JpaRepository<SpotifyMusicArtistRelation, Long> {

}
