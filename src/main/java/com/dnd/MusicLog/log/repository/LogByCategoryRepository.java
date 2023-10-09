package com.dnd.MusicLog.log.repository;

import com.dnd.MusicLog.log.enums.Feeling;
import com.dnd.MusicLog.log.enums.Season;
import com.dnd.MusicLog.log.enums.Time;
import com.dnd.MusicLog.log.enums.Weather;

public interface LogByCategoryRepository {

    Long findRecordCountByCategory(long userId, Feeling feeling, Time time, Weather weather, Season season);

}
