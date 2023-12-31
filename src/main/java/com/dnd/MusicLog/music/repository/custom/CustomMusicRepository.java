package com.dnd.MusicLog.music.repository.custom;

import com.dnd.MusicLog.music.entity.custom.CustomMusic;
import com.dnd.MusicLog.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomMusicRepository extends JpaRepository<CustomMusic, Long> {

    @Query(value = """
        SELECT cm
        FROM CustomMusic cm
        WHERE cm.name LIKE CONCAT("%", :query,"%")
        """)
    List<CustomMusic> searchAllByUserIdAndQuery(
        @Param("query") String query,
        PageRequest pageRequest);

    Optional<CustomMusic> findById(long id);
}
