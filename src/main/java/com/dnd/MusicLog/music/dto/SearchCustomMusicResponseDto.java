package com.dnd.MusicLog.music.dto;

public record SearchCustomMusicResponseDto(int offset, int total, CustomMusicItem[] items) {

    record CustomMusicItem(long id, String name, String artist, String imageUrl) {

    }
}
