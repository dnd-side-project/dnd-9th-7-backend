package com.dnd.MusicLog.music.repository.custom;

import com.dnd.MusicLog.music.entity.custom.CustomMusic;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomMusicRepository extends JpaRepository<CustomMusic, Long> {

    @Query(value = """
        SELECT cm
        FROM CustomMusic cm
        WHERE cm.name LIKE CONCAT("%", :query,"%")
        AND cm.author.id = :userId
        """)
    List<CustomMusic> searchAllByUserIdAndQuery(
        @Param("userId") long userId,
        @Param("query") String query,
        PageRequest pageRequest);

}
