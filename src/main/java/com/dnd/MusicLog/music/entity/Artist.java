package com.dnd.MusicLog.music.entity;

import com.dnd.MusicLog.global.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Artist extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "unique_id", nullable = false)
    private String uniqueId;

    @Column(name = "custom", nullable = false)
    private boolean custom;

    @Column(name = "author")
    private Long author;

    @Builder
    public Artist(String name, String imageUrl, String uniqueId, boolean custom, Long author) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.uniqueId = uniqueId;
        this.custom = custom;
        this.author = author;
    }
}