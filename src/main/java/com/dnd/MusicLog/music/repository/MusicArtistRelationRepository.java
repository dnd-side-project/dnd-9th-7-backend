package com.dnd.MusicLog.music.repository;

import com.dnd.MusicLog.music.entity.Music;
import com.dnd.MusicLog.music.entity.MusicArtistRelation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicArtistRelationRepository extends JpaRepository<MusicArtistRelation, Long> {

    List<MusicArtistRelation> findAllByMusic(Music music);
}
