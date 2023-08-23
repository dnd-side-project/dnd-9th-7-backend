package com.dnd.MusicLog.taginfo.service;

import com.dnd.MusicLog.taginfo.repository.TagInfoCustomRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

//    @Transactional
//    @Test
//    @DisplayName("태그 정보 저장 성공 테스트")
//    void saveTagTest() {
//
//        Log.builder()
//            .date(new Date())
//            .
//        Tag tag = new Tag(Feeling.FLUTTER,Time.DAWN,Weather.RAIN,Season.SPRING);
//
//        TagResponseDto responseDto = tagInfoService.saveTag(tag);
//        assertThat(responseDto).isNotNull();
//    }
//
//    @Transactional
//    @Test
//    @DisplayName("태그 조회 성공 테스트")
//    void searchTagTest() {
//
//        TagInfo tagInfo = TagInfo.builder()
//            .logId(10000) // TODO : 로그 엔티티 생성 후 수정 예정
//            .feeling(Feeling.FLUTTER)
//            .time(Time.DAWN)
//            .weather(Weather.RAIN)
//            .season(Season.SPRING)
//            .build();
//
//        tagInfoRepository.save(tagInfo);
//
//        TagResponseDto responseDto = tagInfoService.searchTag(tagInfo.getLogId());
//        assertThat(responseDto).isNotNull();
//
//    }
//
//    @Transactional
//    @Test
//    @DisplayName("태그별 조회 성공 테스트")
//    void searchTagByTagTest() {
//
//        Tag tag = new Tag(Feeling.FLUTTER, Time.DAWN, Weather.RAIN, Season.SPRING);
//
//        TagResponseDto responseDto = tagInfoService.saveTag(tag);
//
//        Tag tag1 = new Tag(Feeling.FLUTTER, null, null, null);
//        List<TagInfo> tagInfos1 = tagInfoCustomRepository.searchTagInfoByCategory(tag1);
//        Tag tag2 = new Tag(null, Time.DAWN, null, null);
//        List<TagInfo> tagInfos2 = tagInfoCustomRepository.searchTagInfoByCategory(tag2);
//        Tag tag3 = new Tag(null, null, Weather.RAIN, null);
//        List<TagInfo> tagInfos3 = tagInfoCustomRepository.searchTagInfoByCategory(tag3);
//        Tag tag4 = new Tag(null, null, null, Season.SPRING);
//        List<TagInfo> tagInfos4 = tagInfoCustomRepository.searchTagInfoByCategory(tag4);
//
//        assertThat(tagInfos1).isNotNull();
//        assertThat(tagInfos2).isNotNull();
//        assertThat(tagInfos3).isNotNull();
//        assertThat(tagInfos4).isNotNull();
//
//    }
}