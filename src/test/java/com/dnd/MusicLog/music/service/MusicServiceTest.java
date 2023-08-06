package com.dnd.MusicLog.music.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.dnd.MusicLog.music.dto.SpotifyTrackResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MusicServiceTest {

    private final MusicService musicService;

    @Autowired
    public MusicServiceTest(MusicService musicService) {
        this.musicService = musicService;
    }

    @Test
    @DisplayName("음악 검색 API 성공 테스트")
    void searchMusicTest() {
        SpotifyTrackResponseDto track = musicService.searchMusic("superyshy", 0);
        assertThat(track).isNotNull();
    }
}
