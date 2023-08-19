package com.dnd.MusicLog.music.entity.spotify;

import com.dnd.MusicLog.global.common.BaseTimeEntity;
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
public class SpotifyMusicArtistRelation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne(targetEntity = SpotifyMusic.class)
    @JoinColumn(name = "music_id")
    SpotifyMusic music;

    @ManyToOne(targetEntity = SpotifyArtist.class)
    @JoinColumn(name = "artist_id")
    SpotifyArtist artist;

    @Builder
    public SpotifyMusicArtistRelation(SpotifyMusic music, SpotifyArtist artist) {
        this.music = music;
        this.artist = artist;
    }
}
