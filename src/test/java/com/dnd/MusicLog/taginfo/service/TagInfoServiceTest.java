package com.dnd.MusicLog.taginfo.service;

import com.dnd.MusicLog.taginfo.dto.Tag;
import com.dnd.MusicLog.taginfo.dto.TagResponseDto;
import com.dnd.MusicLog.taginfo.entity.TagInfo;
import com.dnd.MusicLog.taginfo.enums.Feeling;
import com.dnd.MusicLog.taginfo.enums.Season;
import com.dnd.MusicLog.taginfo.enums.Time;
import com.dnd.MusicLog.taginfo.enums.Weather;
import com.dnd.MusicLog.taginfo.repository.TagInfoCustomRepositoryImpl;
import com.dnd.MusicLog.taginfo.repository.TagInfoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TagInfoServiceTest {

    private final TagInfoRepository tagInfoRepository;
    private final TagInfoService tagInfoService;
    private final TagInfoCustomRepositoryImpl tagInfoCustomRepository;

    @Autowired
    public TagInfoServiceTest(TagInfoRepository tagInfoRepository, TagInfoService tagInfoService, TagInfoCustomRepositoryImpl tagInfoCustomRepository) {
        this.tagInfoRepository = tagInfoRepository;
        this.tagInfoService = tagInfoService;
        this.tagInfoCustomRepository = tagInfoCustomRepository;
    }

    @Transactional
    @Test
    @DisplayName("태그 정보 저장 성공 테스트")
    void saveTagTest() {

        Tag tag = new Tag("행복", "아침", "맑음", "봄");

        TagResponseDto responseDto = tagInfoService.saveTag(tag);
        assertThat(responseDto).isNotNull();
    }

    @Transactional
    @Test
    @DisplayName("태그 조회 성공 테스트")
    void searchTagTest() {

        TagInfo tagInfo = TagInfo.builder()
            .logId(10000) // TODO : 로그 엔티티 생성 후 수정 예정
            .feeling(Feeling.공허)
            .time(Time.점심)
            .weather(Weather.눈)
            .season(Season.여름)
            .build();

        tagInfoRepository.save(tagInfo);

        TagResponseDto responseDto = tagInfoService.searchTag(tagInfo.getLogId());
        assertThat(responseDto).isNotNull();

    }

    @Transactional
    @Test
    @DisplayName("태그별 조회 성공 테스트")
    void searchTagByTagTest() {

        Tag tag = new Tag("행복", "아침", "맑음", "봄");

        TagResponseDto responseDto = tagInfoService.saveTag(tag);

        Tag tag1 = new Tag(null, null, null, "봄");
        List<TagInfo> tagInfos1 = tagInfoCustomRepository.searchTagInfoByCategory(tag1);
        Tag tag2 = new Tag("행복", null, null, null);
        List<TagInfo> tagInfos2 = tagInfoCustomRepository.searchTagInfoByCategory(tag2);
        Tag tag3 = new Tag(null, "아침", null, null);
        List<TagInfo> tagInfos3 = tagInfoCustomRepository.searchTagInfoByCategory(tag3);
        Tag tag4 = new Tag(null, null, "맑음", null);
        List<TagInfo> tagInfos4 = tagInfoCustomRepository.searchTagInfoByCategory(tag4);

        assertThat(tagInfos1).isNotNull();
        assertThat(tagInfos2).isNotNull();
        assertThat(tagInfos3).isNotNull();
        assertThat(tagInfos4).isNotNull();

    }
}