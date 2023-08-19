package com.dnd.MusicLog.music.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.dnd.MusicLog.music.dto.SaveCustomMusicRequestDto;
import com.dnd.MusicLog.music.dto.SearchCustomMusicResponseDto;
import com.dnd.MusicLog.user.entity.User;
import com.dnd.MusicLog.user.enums.OAuthType;
import com.dnd.MusicLog.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class MusicServiceTest {

    private final MusicService musicService;
    private final UserRepository userRepository;

    @Autowired
    public MusicServiceTest(MusicService musicService, UserRepository userRepository) {
        this.musicService = musicService;
        this.userRepository = userRepository;
    }

    @DisplayName("테스트에 사용 할 사용자와 데이터 생성")
    @BeforeTestClass
    @Test
    public void beforeTest() {
        User user = User.builder()
            .oauthId("1")
            .nickname("admin")
            .oauthType(OAuthType.KAKAO)
            .profileUrl("")
            .email("admin@test.com")
            .build();
        userRepository.save(user);

        SaveCustomMusicRequestDto customMusic01 =
            new SaveCustomMusicRequestDto("hello01", "hello01", "newjeans01");

        SaveCustomMusicRequestDto customMusic02 =
            new SaveCustomMusicRequestDto("hello02", "hello02", "newjeans02");

        SaveCustomMusicRequestDto customMusic03 =
            new SaveCustomMusicRequestDto("hello03", "hello03", "newjeans03");

        musicService.saveCustomMusic(1, customMusic01);
        musicService.saveCustomMusic(1, customMusic02);
        musicService.saveCustomMusic(1, customMusic03);
    }

    @DisplayName("음악 조회 로직 테스트")
    @Transactional
    @Nested
    class SearchTest {

        @DisplayName("hello 이름이 포함된 음악이 검색된다.")
        @Test
        void searchHelloSuccess() {
            SearchCustomMusicResponseDto response = musicService.searchCustomMusic(1, "hello", 0, 10);
            assertThat(response.items()).isNotEmpty();
        }

        @DisplayName("검색 시 size 값이 2라면 최대 2개만 반환된다.")
        @Test
        void searchHelloWithSize2() {
            SearchCustomMusicResponseDto response01 = musicService.searchCustomMusic(1, "hello", 0, 2);
            assertThat(response01.items().size()).isEqualTo(2);
            SearchCustomMusicResponseDto response02 = musicService.searchCustomMusic(1, "hello", 1, 2);
            assertThat(response02.items().size()).isEqualTo(1);
            SearchCustomMusicResponseDto response03 = musicService.searchCustomMusic(1, "hello", 2, 2);
            assertThat(response03.items()).isEmpty();
        }

        @DisplayName("hello01 이름을 가진 음악은 1개만 존재한다.")
        @Test
        void searchHello01ResponseOneObject() {
            SearchCustomMusicResponseDto response = musicService.searchCustomMusic(1, "hello01", 0, 10);
            assertThat(response.items().size()).isEqualTo(1);
        }

        @DisplayName("userId가 2일 때는 검색 결과가 존재하지 않는다.")
        @Test
        void searchHelloWithUserId2() {
            SearchCustomMusicResponseDto response = musicService.searchCustomMusic(2, "hello", 0, 10);
            assertThat(response.items()).isEmpty();
        }
    }
}
