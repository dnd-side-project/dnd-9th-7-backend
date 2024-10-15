package com.dnd.MusicLog.log.repository;

import com.dnd.MusicLog.log.entity.Log;
import com.dnd.MusicLog.log.enums.Feeling;
import com.dnd.MusicLog.log.enums.Season;
import com.dnd.MusicLog.log.enums.Time;
import com.dnd.MusicLog.log.enums.Weather;

import java.util.List;


public interface LogByFilterRepository {

    Long getRecordCountByFilter(long userId, Feeling feeling, Time time, Weather weather, Season season);

    List<Log> getMyPlaylistByFilter(long userId, Feeling feeling, Time time, Weather weather, Season season);

}
