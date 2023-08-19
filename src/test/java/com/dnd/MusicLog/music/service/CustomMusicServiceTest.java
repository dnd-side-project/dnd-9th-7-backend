package com.dnd.MusicLog.music.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.dnd.MusicLog.global.error.exception.BusinessLogicException;
import com.dnd.MusicLog.music.dto.CustomMusicItem;
import com.dnd.MusicLog.music.dto.CustomMusicRequestDto;
import com.dnd.MusicLog.music.dto.CustomMusicResponseDto;
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
class CustomMusicServiceTest {

    private final CustomMusicService customMusicService;
    private final UserRepository userRepository;

    @Autowired
    public CustomMusicServiceTest(CustomMusicService customMusicService, UserRepository userRepository) {
        this.customMusicService = customMusicService;
        this.userRepository = userRepository;
    }

    @DisplayName("테스트에 사용 할 사용자와 데이터 생성")
    @BeforeTestClass
    @Test
    public void beforeTest() {
        User user01 = User.builder()
            .oauthId("1")
            .nickname("user01")
            .oauthType(OAuthType.KAKAO)
            .profileUrl("")
            .email("user01@test.com")
            .build();
        userRepository.save(user01);

        User user02 = User.builder()
            .oauthId("2")
            .nickname("user02")
            .oauthType(OAuthType.KAKAO)
            .profileUrl("")
            .email("user02@test.com")
            .build();
        userRepository.save(user02);

        CustomMusicRequestDto customMusic01 =
            new CustomMusicRequestDto("hello01", "hello01", "newjeans01");

        CustomMusicRequestDto customMusic02 =
            new CustomMusicRequestDto("hello02", "hello02", "newjeans02");

        CustomMusicRequestDto customMusic03 =
            new CustomMusicRequestDto("hello03", "hello03", "newjeans03");

        customMusicService.saveCustomMusic(1, customMusic01);
        customMusicService.saveCustomMusic(1, customMusic02);
        customMusicService.saveCustomMusic(1, customMusic03);
    }

    @DisplayName("음악 조회 로직 테스트")
    @Transactional
    @Nested
    class SearchTest {

        @DisplayName("hello 이름이 포함된 음악이 검색된다.")
        @Test
        void searchHelloSuccess() {
            SearchCustomMusicResponseDto response = customMusicService.searchCustomMusic(1, "hello", 0, 10);
            assertThat(response.items()).isNotEmpty();
        }

        @DisplayName("검색 시 size 값이 2라면 최대 2개만 반환된다.")
        @Test
        void searchHelloWithSize2() {
            SearchCustomMusicResponseDto response01 = customMusicService.searchCustomMusic(1, "hello", 0, 2);
            assertThat(response01.items().size()).isEqualTo(2);
            SearchCustomMusicResponseDto response02 = customMusicService.searchCustomMusic(1, "hello", 1, 2);
            assertThat(response02.items().size()).isEqualTo(1);
            SearchCustomMusicResponseDto response03 = customMusicService.searchCustomMusic(1, "hello", 2, 2);
            assertThat(response03.items()).isEmpty();
        }

        @DisplayName("hello01 이름을 가진 음악은 1개만 존재한다.")
        @Test
        void searchHello01ResponseOneObject() {
            SearchCustomMusicResponseDto response = customMusicService.searchCustomMusic(1, "hello01", 0, 10);
            assertThat(response.items().size()).isEqualTo(1);
        }

        @DisplayName("userId가 2일 때는 검색 결과가 존재하지 않는다.")
        @Test
        void searchHelloWithUserId2() {
            SearchCustomMusicResponseDto response = customMusicService.searchCustomMusic(2, "hello", 0, 10);
            assertThat(response.items()).isEmpty();
        }

        @DisplayName("userId 와 customMusicId 로 음악을 검색하는데 성공한다.")
        @Test
        void searchByUserIdAndCustomMusicId() {
            CustomMusicItem response = customMusicService.searchCustomMusic(1, 1);
            assertThat(response.getName()).isEqualTo("hello01");
            assertThat(response.getImageUrl()).isEqualTo("hello01");
            assertThat(response.getArtist()).isEqualTo("newjeans01");
        }

        @DisplayName("권한이 없는 음악을 조회하면 존재하지 않는 리소스 에러가 발생한다.")
        @Test
        void searchByIdWithInvalidAuthor() {
            assertThatThrownBy(() -> customMusicService.searchCustomMusic(2, 1))
                .isInstanceOf(BusinessLogicException.class);
        }

        @DisplayName("userId가 일치하지 않으면 존재하지 않는 리소스 에러가 발생한다.")
        @Test
        void updateByIdSuccessTest() {
            // given
            CustomMusicRequestDto saveCustomMusicRequestDto =
                new CustomMusicRequestDto("name", "imageUrl", "artist");
            CustomMusicResponseDto saveCustomMusicResponseDto =
                customMusicService.saveCustomMusic(1, saveCustomMusicRequestDto);

            // when
            CustomMusicRequestDto updateCustomMusicRequestDto =
                new CustomMusicRequestDto("updatedName", "updatedImageUrl", "updatedArtist");
            CustomMusicResponseDto updateCustomMusicResponseDto =
                customMusicService.updateCustomMusic(1, saveCustomMusicResponseDto.getId(),
                    updateCustomMusicRequestDto);

            // then
            assertThat(updateCustomMusicResponseDto).isNotEqualTo(saveCustomMusicResponseDto);
            assertThat(updateCustomMusicResponseDto.getName()).isEqualTo("updatedName");
            assertThat(updateCustomMusicResponseDto.getImageUrl()).isEqualTo("updatedImageUrl");
            assertThat(updateCustomMusicResponseDto.getArtist()).isEqualTo("updatedArtist");
        }

        @DisplayName("권한이 없는 음악을 수정하면 존재하지 않는 리소스 에러가 발생한다.")
        @Test
        void updateByIdWithInvalidAuthor() {
            CustomMusicRequestDto request = new CustomMusicRequestDto("name", "image", "artist");
            assertThatThrownBy(() -> customMusicService.updateCustomMusic(2, 1, request))
                .isInstanceOf(BusinessLogicException.class);
        }
    }
}
