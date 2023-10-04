package com.dnd.MusicLog.log.repository;

import com.dnd.MusicLog.log.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {

    Optional<Log> findById(long id);

    Optional<Log> findByIdAndUserId(long id, long userId);

    @Query("SELECT l FROM Log l WHERE l.user.id = :userId AND l.temp = true ORDER BY l.lastModifiedDate DESC")
    List<Log> findByUserIdAndTemp(@Param("userId") long userId);

    // 월별 캘린더 정보 제공 (해당 년도, 월, 앨범 커버 + 일, 기록한 날짜 개수, 기록 개수)
//    @Query("SELECT l " +
//        "FROM Log l " +
//        "WHERE l.user.id = :userId AND l.temp = false " +
//        "GROUP BY l.date ORDER BY l.lastModifiedDate DESC")
//    List<Log> findAllByUserIdAndDate(@Param("userId") long userId, String year, String month);

    // 일별 캘린더 정보 제공
    @Query("SELECT l FROM Log l WHERE l.user.id = :userId AND YEAR(l.date) = YEAR(:date) AND MONTH(l.date) = MONTH(:date) " +
        "AND DAY(l.date) = DAY(:date) AND l.temp = false ORDER BY l.representation DESC ,l.lastModifiedDate DESC")
    List<Log> findAllByUserIdAndDay(@Param("userId") long userId, @Param("date") LocalDate date);
}
