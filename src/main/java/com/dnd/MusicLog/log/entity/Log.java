package com.dnd.MusicLog.log.entity;

import com.dnd.MusicLog.global.common.BaseTimeEntity;
import com.dnd.MusicLog.music.entity.custom.CustomMusic;
import com.dnd.MusicLog.music.entity.spotify.SpotifyMusic;
import com.dnd.MusicLog.music.enums.MusicType;
import com.dnd.MusicLog.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "music_id")
    private SpotifyMusic spotifyMusic;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custom_id")
    private CustomMusic customMusic;

    private String location;

    private String record;

    private String review;

    private String youtubeId;

    @Column(name = "temp", nullable = false)
    private boolean temp;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "music_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private MusicType musicType;

    //로그 with 스포티파이
    public Log(User user, SpotifyMusic spotifyMusic, String location, String record, String review, String youtubeId, boolean temp, Date date, MusicType musicType) {
        this.user = user;
        this.spotifyMusic = spotifyMusic;
        this.location = location;
        this.record = record;
        this.review = review;
        this.youtubeId = youtubeId;
        this.temp = temp;
        this.date = date;
        this.musicType = musicType;
    }
    //로그 with 커스텀
    public Log(User user, CustomMusic customMusic, String location, String record, String review, String youtubeId, boolean temp, Date date, MusicType musicType) {
        this.user = user;
        this.customMusic = customMusic;
        this.location = location;
        this.record = record;
        this.review = review;
        this.youtubeId = youtubeId;
        this.temp = temp;
        this.date = date;
        this.musicType = musicType;
    }
}
