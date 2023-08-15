package com.dnd.MusicLog.music.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.dnd.MusicLog.global.error.exception.BusinessLogicException;
import com.dnd.MusicLog.music.dto.SaveMusicRequestDto.AlbumRequestDto;
import com.dnd.MusicLog.music.dto.SaveMusicRequestDto.ArtistRequestDto;
import com.dnd.MusicLog.music.dto.SaveMusicRequestDto.MusicRequestDto;
import com.dnd.MusicLog.music.dto.SaveMusicResponseDto;
import com.dnd.MusicLog.music.dto.SaveMusicResponseDto.AlbumResponse;
import com.dnd.MusicLog.music.dto.SaveMusicResponseDto.ArtistResponse;
import com.dnd.MusicLog.music.dto.SaveMusicResponseDto.MusicResponse;
import com.dnd.MusicLog.music.dto.SpotifyTrackResponseDto;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class MusicServiceTest {

    private final MusicService musicService;

    @Autowired
    public MusicServiceTest(MusicService musicService) {
        this.musicService = musicService;
    }

    @DisplayName("음악 검색 API 성공 테스트")
    @Test
    void searchMusicTest() {
        SpotifyTrackResponseDto track = musicService.searchMusic("superyshy", 0);
        assertThat(track).isNotNull();
    }

    @DisplayName("음악 저장 API 테스트")
    @Nested
    class SaveTest {

        @DisplayName("아티스트, 앨범 정보가 없는 경우 음악 정보만 저장된다.")
        @Test
        @Transactional
        void saveMusicTest01() {
            // given
            MusicRequestDto musicRequestDto =
                new MusicRequestDto("custom-music-01", null, "music01", true, "2023-01-01");

            // when
            SaveMusicResponseDto response = musicService.saveMusic(
                1,
                musicRequestDto,
                Collections.emptyList(),
                new AlbumRequestDto(null, null, null, false, null));

            // then
            MusicResponse savedMusic = response.getMusic();
            assertThat(savedMusic).isNotNull();
            assertThat(savedMusic.id()).isNotEqualTo(0);
        }

        @DisplayName("음악이 정상적으로 생성된다.")
        @Test
        @Transactional
        void saveMusicTest02() {
            // given
            MusicRequestDto musicRequestDto =
                new MusicRequestDto("custom-music-02", null, "music02", true, "2023-01-01");

            ArtistRequestDto artistRequestDto =
                new ArtistRequestDto("custom-artist-02", null, "artist02", true);

            AlbumRequestDto albumRequestDto =
                new AlbumRequestDto("custom-album-02", null, "album02", true, "2023-01-01");

            // when
            SaveMusicResponseDto response =
                musicService.saveMusic(1, musicRequestDto, List.of(artistRequestDto), albumRequestDto);

            // then
            MusicResponse savedMusic = response.getMusic();
            assertThat(savedMusic).isNotNull();

            AlbumResponse savedAlbum = response.getAlbum();
            assertThat(savedAlbum).isNotNull();

            List<ArtistResponse> savedArtists = response.getArtists();
            assertThat(savedArtists).isNotNull().isNotEmpty();
        }

        @DisplayName("이미 생성된 음악의 uniqueId로 음악을 생성하면 기존 정보가 반환된다.")
        @Test
        @Transactional
        void saveMusicTest03() {
            // given
            MusicRequestDto musicRequestDto =
                new MusicRequestDto("custom-music-03", null, "music03", true, "2023-01-01");

            ArtistRequestDto artistRequestDto =
                new ArtistRequestDto("custom-artist-03", null, "artist03", true);

            AlbumRequestDto albumRequestDto =
                new AlbumRequestDto("custom-album-03", null, "album03", true, "2023-01-01");

            // when
            SaveMusicResponseDto response01 =
                musicService.saveMusic(1, musicRequestDto, List.of(artistRequestDto), albumRequestDto);

            SaveMusicResponseDto response02 =
                musicService.saveMusic(
                    1,
                    musicRequestDto, List.of(),
                    new AlbumRequestDto(null, null, null, false, null));

            // then
            assertThat(response01).isEqualTo(response02);
        }

        @DisplayName("uniqueId 없이 음악 생성을 시도하면 실패한다.")
        @Test
        @Transactional
        void saveMusicTest04() {
            // given
            MusicRequestDto musicRequestDto =
                new MusicRequestDto("custom-music-04", null, null, true, "2023-01-01");

            ArtistRequestDto artistRequestDto =
                new ArtistRequestDto("custom-artist-04", null, "artist04", true);

            AlbumRequestDto albumRequestDto =
                new AlbumRequestDto("custom-album-04", null, "album04", true, "2023-01-01");

            // when
            // then
            assertThatThrownBy(
                () -> musicService.saveMusic(1, musicRequestDto, List.of(artistRequestDto), albumRequestDto))
                .isInstanceOf(BusinessLogicException.class);
        }
    }
}
