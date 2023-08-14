package com.dnd.MusicLog.music.entity;

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
public class MusicArtistRelation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    @JoinColumn(name = "music_id")
    Music music;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    Artist artist;

    @Builder
    public MusicArtistRelation(Music music, Artist artist) {
        this.music = music;
        this.artist = artist;
    }
}
