package com.dnd.MusicLog.music.repository;

import com.dnd.MusicLog.music.entity.Artist;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

    Optional<Artist> findByAuthorAndUniqueId(long author, String uniqueId);

    Optional<Artist> findByUniqueId(String uniqueId);
}
