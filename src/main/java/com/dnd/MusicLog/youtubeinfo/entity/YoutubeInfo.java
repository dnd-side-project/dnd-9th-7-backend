package com.dnd.MusicLog.youtubeinfo.entity;

import com.dnd.MusicLog.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class YoutubeInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String videoId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String channelTitle;

    @Column(nullable = false)
    private String publishedAt;

    @Builder
    public YoutubeInfo(String videoId, String title, String channelTitle, String publishedAt) {
        this.videoId = videoId;
        this.title = title;
        this.channelTitle = channelTitle;
        this.publishedAt = publishedAt;
    }
}
