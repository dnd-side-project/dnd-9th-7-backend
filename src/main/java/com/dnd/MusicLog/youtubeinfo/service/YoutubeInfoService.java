package com.dnd.MusicLog.youtubeinfo.service;

import com.dnd.MusicLog.global.error.exception.BusinessLogicException;
import com.dnd.MusicLog.global.error.exception.ErrorCode;
import com.dnd.MusicLog.log.dto.SaveLogRequestDto;
import com.dnd.MusicLog.youtubeinfo.entity.YoutubeInfo;
import com.dnd.MusicLog.youtubeinfo.repository.YoutubeInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class YoutubeInfoService {

    private final YoutubeInfoRepository youtubeInfoRepository;

    @Transactional
    public YoutubeInfo saveYoutubeInfo(SaveLogRequestDto requestDto) {

        if (requestDto.title() != null && requestDto.channelTitle() != null
            && requestDto.publishedAt() != null) {

            YoutubeInfo youtubeInfo = YoutubeInfo.builder()
                .videoId(requestDto.videoId())
                .title(requestDto.title())
                .channelTitle(requestDto.channelTitle())
                .publishedAt(requestDto.publishedAt())
                .build();

            return youtubeInfoRepository.save(youtubeInfo);

        } else {
            throw new BusinessLogicException(ErrorCode.BAD_REQUEST);
        }
    }
}
