package com.dnd.MusicLog.log.repository;

import com.dnd.MusicLog.log.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LogRepository extends JpaRepository<Log, Long> {

    Optional<Log> findById(long id);
}
