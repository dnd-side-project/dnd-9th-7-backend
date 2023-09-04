package com.dnd.MusicLog.log.service;

import com.dnd.MusicLog.log.dto.YoutubeVideoListResponseDto;
import com.dnd.MusicLog.youtubeinfo.service.SearchYoutubeVideosService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SearchYoutubeVideosServiceTest {

    private final SearchYoutubeVideosService searchYoutubeVideosService;

    @Autowired
    public SearchYoutubeVideosServiceTest(SearchYoutubeVideosService searchYoutubeVideosService) {
        this.searchYoutubeVideosService = searchYoutubeVideosService;
    }

    @Test
    @DisplayName("유튜브 검색 API 성공 테스트")
    void searchMusicTest() {
        YoutubeVideoListResponseDto responseDto = searchYoutubeVideosService.searchYoutubeVideos("bts", null);
        assertThat(responseDto).isNotNull();
    }
}