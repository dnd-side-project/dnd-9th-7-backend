package com.dnd.MusicLog.log.repository;

import com.dnd.MusicLog.log.entity.Log;
import com.dnd.MusicLog.log.enums.Feeling;
import com.dnd.MusicLog.log.enums.Season;
import com.dnd.MusicLog.log.enums.Time;
import com.dnd.MusicLog.log.enums.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    // 기록 날짜 개수 및 기록 개수
    @Query(value = """
    SELECT 
        (SELECT COUNT(distinct (log.date)) FROM log WHERE YEAR(date) = YEAR(:date) AND MONTH(date) = MONTH(:date) AND temp = false AND log.user_id = :userId),
        (SELECT COUNT(distinct (log.id)) FROM log WHERE YEAR(date) = YEAR(:date) AND MONTH(date) = MONTH(:date) AND temp = false AND log.user_id = :userId)
""", nativeQuery = true)
    Object[] findDayCountAndRecordCountInfo(@Param("userId") long userId, @Param("date") LocalDate date);


    // 월별 캘린더 정보 제공
    @Query(value = """
    WITH ranked_logs AS (
        SELECT *,
               ROW_NUMBER() OVER (PARTITION BY date
                   ORDER BY representation DESC, last_modified_date DESC) AS rn
        FROM log
        WHERE YEAR(date) = YEAR(:date) AND MONTH(date) = MONTH(:date) AND temp = false AND user_id = :userId
    )
    
    SELECT *
    FROM log
    WHERE id in (select id from ranked_logs where rn = 1 )
""", nativeQuery = true)
    List<Log> findAllByUserIdAndMonth(@Param("userId") long userId, @Param("date") LocalDate date);

    // 일별 캘린더 정보 제공
    @Query("SELECT l FROM Log l left join fetch l.customMusic c left join fetch l.spotifyMusic s WHERE l.user.id = :userId AND YEAR(l.date) = YEAR(:date) AND MONTH(l.date) = MONTH(:date) " +
        "AND DAY(l.date) = DAY(:date) AND l.temp = false ORDER BY l.representation DESC ,l.lastModifiedDate DESC")
    List<Log> findAllByUserIdAndDay(@Param("userId") long userId, @Param("date") LocalDate date);

    // 대표 이미지 설정 및 변경
    @Modifying
    @Query(value = "UPDATE log l SET l.representation = CASE WHEN l.id = :logId THEN true ELSE false END WHERE l.user_id = :userId " +
        "AND l.temp = false AND YEAR(l.date) = YEAR(:date) AND MONTH(l.date) = MONTH(:date) AND DAY(l.date) = DAY(:date)", nativeQuery = true)
    void updateRepresentationImage(@Param("userId") long userId, @Param("date") LocalDate date, @Param("logId") long logId);

    @Query("SELECT DISTINCT l.feeling FROM Log l WHERE l.user.id = :userId AND l.temp = false")
    List<Feeling> findDistinctFeelings(@Param("userId") long userId);

    @Query("SELECT DISTINCT l.time FROM Log l WHERE l.user.id = :userId AND l.temp = false")
    List<Time> findDistinctTimes(@Param("userId") long userId);

    @Query("SELECT DISTINCT l.weather FROM Log l WHERE l.user.id = :userId AND l.temp = false")
    List<Weather> findDistinctWeathers(@Param("userId") long userId);

    @Query("SELECT DISTINCT l.season FROM Log l WHERE l.user.id = :userId AND l.temp = false")
    List<Season> findDistinctSeasons(@Param("userId") long userId);

}
