package com.dnd.MusicLog.taginfo.repository;

import com.dnd.MusicLog.taginfo.dto.Tag;
import com.dnd.MusicLog.taginfo.entity.QTagInfo;
import com.dnd.MusicLog.taginfo.entity.TagInfo;
import com.dnd.MusicLog.taginfo.enums.Feeling;
import com.dnd.MusicLog.taginfo.enums.Season;
import com.dnd.MusicLog.taginfo.enums.Time;
import com.dnd.MusicLog.taginfo.enums.Weather;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.dnd.MusicLog.taginfo.entity.QTagInfo.tagInfo;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
@Repository
public class TagInfoCustomRepositoryImpl implements TagInfoCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<TagInfo> searchTagInfoByCategory(Tag tag) {

        return jpaQueryFactory
            .selectFrom(tagInfo)
            .where(feelingEq(tag.feeling()),
                timeEq(tag.time()),
                weatherEq(tag.weather()),
                seasonEq(tag.season()))
            .fetch();

    }

    private BooleanExpression feelingEq(Feeling feeling) {
        return feeling != null ? tagInfo.feeling.eq(feeling) : null;
    }
    private BooleanExpression timeEq(Time time) {
        return time != null ? tagInfo.time.eq(time) : null;
    }

    private BooleanExpression weatherEq(Weather weather) {
        return weather != null ? tagInfo.weather.eq(weather) : null;
    }

    private BooleanExpression seasonEq(Season season) {
        return season != null ? tagInfo.season.eq(season) : null;
    }
}
