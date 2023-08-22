package com.dnd.MusicLog.taginfo.entity;

import com.dnd.MusicLog.global.common.BaseTimeEntity;
import com.dnd.MusicLog.log.entity.Log;
import com.dnd.MusicLog.taginfo.enums.Feeling;
import com.dnd.MusicLog.taginfo.enums.Season;
import com.dnd.MusicLog.taginfo.enums.Time;
import com.dnd.MusicLog.taginfo.enums.Weather;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "tag_info")
@Entity
public class TagInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "log_id")
    private Log log;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Feeling feeling;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Time time;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Weather weather;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Season season;

    @Builder
    public TagInfo(Log log, Feeling feeling, Time time, Weather weather, Season season) {
        this.log = log;
        this.feeling = feeling;
        this.time = time;
        this.weather = weather;
        this.season = season;
    }
}
