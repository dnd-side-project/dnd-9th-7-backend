package com.dnd.MusicLog.log.service;

import com.dnd.MusicLog.global.error.exception.BusinessLogicException;
import com.dnd.MusicLog.global.error.exception.ErrorCode;
import com.dnd.MusicLog.imageinfo.dto.FileNamesResponseDto;
import com.dnd.MusicLog.imageinfo.repository.ImageInfoRepository;
import com.dnd.MusicLog.imageinfo.service.ImageInfoService;
import com.dnd.MusicLog.log.dto.GetLogRecordResponseDto;
import com.dnd.MusicLog.log.dto.SaveLogRequestDto;
import com.dnd.MusicLog.log.entity.Log;
import com.dnd.MusicLog.log.repository.LogRepository;
import com.dnd.MusicLog.music.enums.MusicType;
import com.dnd.MusicLog.user.entity.User;
import com.dnd.MusicLog.user.service.OAuthLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LogService {

    private final OAuthLoginService oAuthLoginService;
    private final ImageInfoService imageInfoService;
    private final LogRepository logRepository;
    private final ImageInfoRepository imageInfoRepository;

    @Transactional
    public void saveLog(long userId, SaveLogRequestDto requestDto, List<MultipartFile> multipartFile) {
        User user = oAuthLoginService.getUser(userId);

        // 공통 프로퍼티 부분
        Log log = new Log(user, requestDto.location(), requestDto.record(), requestDto.review(),
            requestDto.feeling(), requestDto.time(), requestDto.weather(), requestDto.season(),
            requestDto.youtubeId(), requestDto.title(), requestDto.channelTitle(), requestDto.publishedAt(),
            requestDto.temp(), requestDto.date(), requestDto.musicType());

        //로그 with 스포티파이
        if (requestDto.musicType().equals(MusicType.SPOTIFY)) {
            //TODO : 스포티 파이 음악 정보 중복 검사 및 스포티파이 음악, 앨범, 아티스트 저장 로직 필요.
            //TODO : 로그 인스턴스에 스포티파이 음악 세팅 -> log.setSpotifyMusic(SpotifyMusic);
        }
        //로그 with 커스텀
        if (requestDto.musicType().equals(MusicType.CUSTOM)) {
            //TODO : 커스텀 음악 정보 중복 검사 및 커스텀 음악 저장 로직 필요.
            //TODO : 로그 인스턴스에 커스텀음악 세팅 -> log.setCustomMusic(CustomMusic);
        }

        long logId = logRepository.save(log).getId();

        FileNamesResponseDto responseDto = imageInfoService.uploadImages(logId, multipartFile);

    }

    // 기록 보기 2페이지 - RECORD
    public GetLogRecordResponseDto getLogRecord(long userId, long logId) {

        Log log = logRepository.findByIdAndUserId(userId, logId).orElseThrow(() -> {
            throw new BusinessLogicException(ErrorCode.NOT_FOUND);
        });

        List<String> fileNameList = imageInfoRepository.findAllByLogIdOrderByCreatedDateAsc(logId);

        return new GetLogRecordResponseDto(log.getRecord(), new FileNamesResponseDto(fileNameList));
    }
}
