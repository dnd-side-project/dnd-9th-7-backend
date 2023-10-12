package com.dnd.MusicLog.log.repository;

import com.dnd.MusicLog.log.entity.Log;
import com.dnd.MusicLog.log.enums.Feeling;
import com.dnd.MusicLog.log.enums.Season;
import com.dnd.MusicLog.log.enums.Time;
import com.dnd.MusicLog.log.enums.Weather;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.dnd.MusicLog.log.entity.QLog.log;

@Repository
@RequiredArgsConstructor
public class LogByCategoryRepositoryImpl implements LogByCategoryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long getRecordCountByCategory(long userId, Feeling feeling, Time time, Weather weather, Season season) {
        return jpaQueryFactory
            .select(log.count())
            .from(log)
            .where(log.user.id.eq(userId),
                log.temp.eq(false),
                feelingEq(feeling),
                timeEq(time),
                weatherEq(weather),
                seasonEq(season))
            .fetchCount();
    }

    @Override
    public List<Log> getMyPlaylistByCategory(long userId, Feeling feeling, Time time, Weather weather, Season season) {
        List<Log> logList = jpaQueryFactory
            .selectFrom(log)
            .leftJoin(log.customMusic).fetchJoin()
            .leftJoin(log.spotifyMusic).fetchJoin()
            .leftJoin(log.spotifyMusic.album).fetchJoin()
            .where(log.user.id.eq(userId),
                log.temp.eq(false),
                feelingEq(feeling),
                timeEq(time),
                weatherEq(weather),
                seasonEq(season))
            .fetch();

        Collections.shuffle(logList);
        return logList.stream().limit(5).collect(Collectors.toList());
    }

    private BooleanExpression feelingEq(Feeling feeling) {
        return feeling != null ? log.feeling.eq(feeling) : null;
    }
    private BooleanExpression timeEq(Time time) {
        return time != null ? log.time.eq(time) : null;
    }

    private BooleanExpression weatherEq(Weather weather) {
        return weather != null ? log.weather.eq(weather) : null;
    }

    private BooleanExpression seasonEq(Season season) {
        return season != null ? log.season.eq(season) : null;
    }
}
