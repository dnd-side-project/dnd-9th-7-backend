package com.dnd.MusicLog.taginfo.service;

import com.dnd.MusicLog.global.error.exception.BusinessLogicException;
import com.dnd.MusicLog.global.error.exception.ErrorCode;
import com.dnd.MusicLog.taginfo.dto.Tag;
import com.dnd.MusicLog.taginfo.dto.TagResponseDto;
import com.dnd.MusicLog.taginfo.entity.TagInfo;
import com.dnd.MusicLog.taginfo.enums.Feeling;
import com.dnd.MusicLog.taginfo.enums.Season;
import com.dnd.MusicLog.taginfo.enums.Time;
import com.dnd.MusicLog.taginfo.enums.Weather;
import com.dnd.MusicLog.taginfo.repository.TagInfoCustomRepositoryImpl;
import com.dnd.MusicLog.taginfo.repository.TagInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.dnd.MusicLog.taginfo.entity.QTagInfo.tagInfo;

@RequiredArgsConstructor
@Service
public class TagInfoService {

    private final TagInfoRepository tagInfoRepository;
    private final TagInfoCustomRepositoryImpl tagInfoCustomRepository;

    @Transactional
    public TagResponseDto saveTag(Tag tag) {

        TagInfo tagInfo = TagInfo.builder()
            .logId(1) // TODO : 로그 엔티티 생성 후 수정 예정
            .feeling(Feeling.valueOf(tag.feeling()))
            .time(Time.valueOf(tag.time()))
            .weather(Weather.valueOf(tag.weather()))
            .season(Season.valueOf(tag.season()))
            .build();

        tagInfoRepository.save(tagInfo);

        return new TagResponseDto(tagInfo);

    }

    @Transactional(readOnly = true)
    public TagResponseDto searchTag(long logId) {

        TagInfo tagInfo = tagInfoRepository.findByLogId(logId)
            .orElseThrow(() -> new BusinessLogicException(ErrorCode.NOT_FOUND));

        return new TagResponseDto(tagInfo);
    }

    @Transactional(readOnly = true)
    public List<TagInfo> searchTagInfoByCategory(Tag tag) {

        List<TagInfo> tagInfos = tagInfoCustomRepository.searchTagInfoByCategory(tag);

        return tagInfos;
    }

}