package com.dnd.MusicLog.music.repository;

import com.dnd.MusicLog.music.entity.Music;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Music, Long> {

    Optional<Music> findByAuthorAndUniqueId(long author, String uniqueId);

    Optional<Music> findByUniqueId(String uniqueId);
}
