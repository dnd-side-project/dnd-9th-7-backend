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

    private BooleanExpression feelingEq(String feeling) {
        return hasText(feeling) ? tagInfo.feeling.eq(Feeling.valueOf(feeling)) : null;
    }
    private BooleanExpression timeEq(String time) {
        return hasText(time) ? tagInfo.time.eq(Time.valueOf(time)) : null;
    }
    private BooleanExpression weatherEq(String weather) {
        return hasText(weather) ? tagInfo.weather.eq(Weather.valueOf(weather)) : null;
    }
    private BooleanExpression seasonEq(String season) {
        return hasText(season) ? tagInfo.season.eq(Season.valueOf(season)) : null;
    }
}
