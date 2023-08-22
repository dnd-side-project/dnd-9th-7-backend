package com.dnd.MusicLog.log.entity;

import com.dnd.MusicLog.global.common.BaseTimeEntity;
import com.dnd.MusicLog.music.entity.custom.CustomMusic;
import com.dnd.MusicLog.music.entity.spotify.SpotifyMusic;
import com.dnd.MusicLog.music.enums.MusicType;
import com.dnd.MusicLog.user.entity.User;
import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "music_id")
    private SpotifyMusic spotifyMusic;

    @ManyToOne(fetch = FetchType.LAZY)
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

    public Log(User user, String location, String record, String review, String youtubeId, boolean temp, Date date, MusicType musicType) {
        this.user = user;
        this.location = location;
        this.record = record;
        this.review = review;
        this.youtubeId = youtubeId;
        this.temp = temp;
        this.date = date;
        this.musicType = musicType;
    }

}
