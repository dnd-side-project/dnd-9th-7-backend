package com.dnd.MusicLog.music.entity.spotify;

import com.dnd.MusicLog.global.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class SpotifyAlbum extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "spotify_id", nullable = false)
    private String spotifyId;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "release_date")
    private String releaseDate;
}
