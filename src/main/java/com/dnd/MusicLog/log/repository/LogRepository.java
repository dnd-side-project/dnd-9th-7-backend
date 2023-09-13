package com.dnd.MusicLog.log.repository;

import com.dnd.MusicLog.log.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {

    Optional<Log> findById(long id);

    Optional<Log> findByIdAndUserId(long id, long userId);

    @Query("SELECT l FROM Log l WHERE l.user.id = :userId AND l.temp = true ORDER BY l.lastModifiedDate DESC")
    List<Log> findByUserIdAndTemp(@Param("userId") long userId);

}
