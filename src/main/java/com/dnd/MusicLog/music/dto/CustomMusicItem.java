package com.dnd.MusicLog.music.dto;

import com.dnd.MusicLog.music.entity.custom.CustomMusic;
import lombok.Value;

@Value
public class CustomMusicItem {

    long id;
    String name;
    String artist;
    String imageUrl;

    public CustomMusicItem(CustomMusic customMusic) {
        this.id = customMusic.getId();
        this.name = customMusic.getName();
        this.artist = customMusic.getArtist();
        this.imageUrl = customMusic.getImageUrl();
    }
}
