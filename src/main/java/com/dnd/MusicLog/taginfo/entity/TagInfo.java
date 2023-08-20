package com.dnd.MusicLog.taginfo.entity;

import com.dnd.MusicLog.global.common.BaseTimeEntity;
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

    // TODO : 로그 엔티티 생성 후 연관관계 설정할 것.
    @Column(name = "log_id", nullable = false)
    private long logId;

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
    public TagInfo(long logId, Feeling feeling, Time time, Weather weather, Season season) {
        this.logId = logId;
        this.feeling = feeling;
        this.time = time;
        this.weather = weather;
        this.season = season;
    }

}
