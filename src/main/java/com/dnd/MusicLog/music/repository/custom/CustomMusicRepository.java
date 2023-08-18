package com.dnd.MusicLog.music.repository.custom;

import com.dnd.MusicLog.music.entity.custom.CustomMusic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomMusicRepository extends JpaRepository<CustomMusic, Long> {

}
