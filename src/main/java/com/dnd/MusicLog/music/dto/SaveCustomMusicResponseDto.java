package com.dnd.MusicLog.music.dto;

import com.dnd.MusicLog.music.entity.custom.CustomMusic;
import lombok.Value;

@Value
public class SaveCustomMusicResponseDto {

    long id;
    String name;
    String imageUrl;
    String artist;

    public SaveCustomMusicResponseDto(CustomMusic customMusic) {
        this.id = customMusic.getId();
        this.name = customMusic.getName();
        this.imageUrl = customMusic.getImageUrl();
        this.artist = customMusic.getArtist();
    }
}
