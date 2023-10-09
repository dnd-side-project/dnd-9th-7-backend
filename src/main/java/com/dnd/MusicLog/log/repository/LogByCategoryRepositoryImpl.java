package com.dnd.MusicLog.log.repository;

import com.dnd.MusicLog.log.enums.Feeling;
import com.dnd.MusicLog.log.enums.Season;
import com.dnd.MusicLog.log.enums.Time;
import com.dnd.MusicLog.log.enums.Weather;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.dnd.MusicLog.log.entity.QLog.log;

@Repository
@RequiredArgsConstructor
public class LogByCategoryRepositoryImpl implements LogByCategoryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long findRecordCountByCategory(long userId, Feeling feeling, Time time, Weather weather, Season season) {
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
