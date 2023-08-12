package com.dnd.MusicLog.music.repository;

import com.dnd.MusicLog.music.entity.Album;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    Optional<Album> findByAuthorAndUniqueId(long author, String uniqueId);

    Optional<Album> findByUniqueId(String uniqueId);
}
