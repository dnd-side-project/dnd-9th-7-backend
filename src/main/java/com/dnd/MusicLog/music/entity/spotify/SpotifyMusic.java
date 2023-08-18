package com.dnd.MusicLog.music.entity.spotify;

import com.dnd.MusicLog.global.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class SpotifyMusic extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "spotify_id", nullable = false)
    private String spotifyId;

    @ManyToOne(targetEntity = SpotifyAlbum.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    private SpotifyAlbum album;

    @Builder
    public SpotifyMusic(String name, String spotifyId) {
        this.name = name;
        this.spotifyId = spotifyId;
    }

    public void intoAlbum(SpotifyAlbum album) {
        this.album = album;
    }
}
