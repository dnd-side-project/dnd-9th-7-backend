package com.dnd.MusicLog.music.repository.spotify;

import com.dnd.MusicLog.music.entity.spotify.SpotifyMusic;
import com.dnd.MusicLog.music.entity.spotify.SpotifyMusicArtistRelation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpotifyMusicArtistRelationRepository extends JpaRepository<SpotifyMusicArtistRelation, Long> {

    List<SpotifyMusicArtistRelation> findAllByMusic(SpotifyMusic music);
}
