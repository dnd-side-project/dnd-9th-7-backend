package com.dnd.MusicLog.log.repository;

import com.dnd.MusicLog.log.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {

    Optional<Log> findById(long id);

    Optional<Log> findByIdAndUserId(long id, long userId);


}
