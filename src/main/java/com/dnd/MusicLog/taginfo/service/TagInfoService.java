//package com.dnd.MusicLog.taginfo.service;
//
//import com.dnd.MusicLog.global.error.exception.BusinessLogicException;
//import com.dnd.MusicLog.global.error.exception.ErrorCode;
//import com.dnd.MusicLog.log.entity.Log;
//import com.dnd.MusicLog.log.repository.LogRepository;
//import com.dnd.MusicLog.taginfo.dto.Tag;
//import com.dnd.MusicLog.taginfo.dto.TagResponseDto;
//import com.dnd.MusicLog.taginfo.entity.TagInfo;
//import com.dnd.MusicLog.taginfo.repository.TagInfoCustomRepositoryImpl;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//@Service
//public class TagInfoService {
//
//    private final TagInfoRepository tagInfoRepository;
//    private final TagInfoCustomRepositoryImpl tagInfoCustomRepository;
//    private final LogRepository logRepository;
//
//    @Transactional
//    public long saveTag(long logId, Tag tag) {
//
//        Log log = logRepository.findById(logId).orElseThrow(() -> {
//            throw new BusinessLogicException(ErrorCode.NOT_FOUND);
//        });
//
//        TagInfo tagInfo = TagInfo.builder()
//            .log(log)
//            .feeling(tag.feeling())
//            .time(tag.time())
//            .weather(tag.weather())
//            .season(tag.season())
//            .build();
//
//        return tagInfoRepository.save(tagInfo).getId();
//
//    }
//
//    @Transactional(readOnly = true)
//    public TagResponseDto searchTag(long logId) {
//
//        TagInfo tagInfo = tagInfoRepository.findByLogId(logId)
//            .orElseThrow(() -> new BusinessLogicException(ErrorCode.NOT_FOUND));
//
//        return new TagResponseDto(tagInfo);
//    }
//
//    @Transactional(readOnly = true)
//    public List<TagInfo> searchTagInfoByCategory(Tag tag) {
//
//        List<TagInfo> tagInfos = tagInfoCustomRepository.searchTagInfoByCategory(tag);
//
//        return tagInfos;
//    }
//
//}
