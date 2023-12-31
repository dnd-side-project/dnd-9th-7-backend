package com.dnd.MusicLog.log.entity;

import com.dnd.MusicLog.global.common.BaseTimeEntity;
import com.dnd.MusicLog.log.enums.Feeling;
import com.dnd.MusicLog.log.enums.Season;
import com.dnd.MusicLog.log.enums.Time;
import com.dnd.MusicLog.log.enums.Weather;
import com.dnd.MusicLog.music.entity.custom.CustomMusic;
import com.dnd.MusicLog.music.entity.spotify.SpotifyMusic;
import com.dnd.MusicLog.music.enums.MusicType;
import com.dnd.MusicLog.user.entity.User;
import com.dnd.MusicLog.youtubeinfo.entity.YoutubeInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class Log extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "music_id")
    private SpotifyMusic spotifyMusic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custom_id")
    private CustomMusic customMusic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "youtube_id")
    private YoutubeInfo youtubeInfo;

    private String location;

    private String record;

    private String review;

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

    private boolean representation = false;

    @Column(name = "temp", nullable = false)
    private boolean temp;

    @Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "music_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private MusicType musicType;

    public Log(User user, String location, String record, String review, Feeling feeling, Time time, Weather weather,
               Season season, boolean temp, LocalDate date, MusicType musicType) {
        this.user = user;
        this.location = location;
        this.record = record;
        this.review = review;
        this.feeling = feeling;
        this.time = time;
        this.weather = weather;
        this.season = season;
        this.temp = temp;
        this.date = date;
        this.musicType = musicType;
    }

    public void updateLogInfo(String location, String record, String review, Feeling feeling, Time time, Weather weather, Season season,
               boolean temp, LocalDate date, MusicType musicType) {
        this.location = location;
        this.record = record;
        this.review = review;
        this.feeling = feeling;
        this.time = time;
        this.weather = weather;
        this.season = season;
        this.temp = temp;
        this.date = date;
        this.musicType = musicType;
    }

    public void setSpotifyMusic(SpotifyMusic spotifyMusic){
        this.spotifyMusic = spotifyMusic;
    }

    public void setCustomMusic(CustomMusic customMusic){
        this.customMusic = customMusic;
    }

    public void setYoutubeInfo(YoutubeInfo youtubeInfo){
        this.youtubeInfo = youtubeInfo;
    }

}
