package com.dnd.MusicLog.music.entity.custom;

import com.dnd.MusicLog.global.common.BaseTimeEntity;
import com.dnd.MusicLog.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class CustomMusic extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "artist", nullable = false)
    private String artist;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User author;

    @Builder
    public CustomMusic(String name, String imageUrl, String artist, User author) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.artist = artist;
        this.author = author;
    }

    public void updateStaticInfo(String name, String artist, String imageUrl) {
        this.name = name;
        this.artist = artist;
        this.imageUrl = imageUrl;
    }
}
