package com.dnd.MusicLog.log.service;

import com.dnd.MusicLog.global.error.exception.BusinessLogicException;
import com.dnd.MusicLog.global.error.exception.ErrorCode;
import com.dnd.MusicLog.imageinfo.repository.ImageInfoRepository;
import com.dnd.MusicLog.imageinfo.service.ImageInfoService;
import com.dnd.MusicLog.log.dto.*;
import com.dnd.MusicLog.log.entity.Log;
import com.dnd.MusicLog.log.repository.LogRepository;
import com.dnd.MusicLog.music.dto.CustomMusicRequestDto;
import com.dnd.MusicLog.music.dto.CustomMusicResponseDto;
import com.dnd.MusicLog.music.dto.SpotifyItemResponse;
import com.dnd.MusicLog.music.entity.custom.CustomMusic;
import com.dnd.MusicLog.music.entity.spotify.SpotifyMusic;
import com.dnd.MusicLog.music.enums.MusicType;
import com.dnd.MusicLog.music.repository.custom.CustomMusicRepository;
import com.dnd.MusicLog.music.repository.spotify.SpotifyMusicRepository;
import com.dnd.MusicLog.music.service.CustomMusicService;
import com.dnd.MusicLog.music.service.SpotifyMusicService;
import com.dnd.MusicLog.user.entity.User;
import com.dnd.MusicLog.user.service.OAuthLoginService;
import com.dnd.MusicLog.youtubeinfo.entity.YoutubeInfo;
import com.dnd.MusicLog.youtubeinfo.service.YoutubeInfoService;
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
    private final YoutubeInfoService youtubeInfoService;
    private final SpotifyMusicService spotifyMusicService;
    private final CustomMusicService customMusicService;
    private final LogRepository logRepository;
    private final ImageInfoRepository imageInfoRepository;
    private final SpotifyMusicRepository spotifyMusicRepository;
    private final CustomMusicRepository customMusicRepository;

    @Transactional
    public SaveLogResponseDto saveLog(long userId, SaveLogRequestDto requestDto, List<MultipartFile> multipartFile) {
        User user = oAuthLoginService.getUser(userId);

        // 공통 프로퍼티 부분
        Log log = new Log(user, requestDto.location(), requestDto.record(), requestDto.review(),
            requestDto.feeling(), requestDto.time(), requestDto.weather(), requestDto.season(),
            requestDto.temp(), requestDto.date(), requestDto.musicType());

        // 유튜브 저장 부분
        if (requestDto.videoId() != null) {
            YoutubeInfo youtubeInfo = youtubeInfoService.getOrCreateYoutubeInfo(requestDto);
            log.setYoutubeInfo(youtubeInfo);
        }

        // 로그 with 스포티파이
        if (requestDto.musicType().equals(MusicType.SPOTIFY)) {

            SpotifyItemResponse spotifyItemResponse = spotifyMusicService.searchSpotifyTrack(requestDto.spotifyId());
            SpotifyMusic spotifyMusic = spotifyMusicRepository.findBySpotifyId(spotifyItemResponse.id()).orElseThrow(() -> {
                throw new BusinessLogicException(ErrorCode.NOT_FOUND);
            });
            log.setSpotifyMusic(spotifyMusic);

        }
        // 로그 with 커스텀
        if (requestDto.musicType().equals(MusicType.CUSTOM)) {

            CustomMusicRequestDto customMusicRequestDto = new CustomMusicRequestDto(requestDto.name(),
                requestDto.imageUrl(), requestDto.artist());
            CustomMusicResponseDto customMusicResponseDto = customMusicService.saveCustomMusic(customMusicRequestDto);
            CustomMusic customMusic = customMusicRepository.findById(customMusicResponseDto.getId()).orElseThrow(() -> {
                throw new BusinessLogicException(ErrorCode.NOT_FOUND);
            });
            log.setCustomMusic(customMusic);

        }

        long logId = logRepository.save(log).getId();

        imageInfoService.uploadImages(logId, multipartFile);

        return new SaveLogResponseDto(logId);
    }

    // 수정 서비스 (커스텀 and 로그 정보 and 이미지)
    @Transactional
    public void updateLog(long userId, long logId, SaveLogRequestDto requestDto, List<MultipartFile> multipartFile) {

        Log log = logRepository.findByIdAndUserId(logId, userId).orElseThrow(() -> {
            throw new BusinessLogicException(ErrorCode.NOT_FOUND);
        });

        // 로그 정보 업데이트
        log.updateLogInfo(requestDto.location(), requestDto.record(), requestDto.review(),
            requestDto.feeling(), requestDto.time(), requestDto.weather(), requestDto.season(),
            requestDto.temp(), requestDto.date(), requestDto.musicType());

        // 유튜브 저장 부분
        if (requestDto.videoId() != null) {
            YoutubeInfo youtubeInfo = youtubeInfoService.getOrCreateYoutubeInfo(requestDto);
            log.setYoutubeInfo(youtubeInfo);
        }

        // 로그 with 스포티파이
        if (requestDto.musicType().equals(MusicType.SPOTIFY)) {

            log.setCustomMusic(null);
            SpotifyItemResponse spotifyItemResponse = spotifyMusicService.searchSpotifyTrack(requestDto.spotifyId());
            SpotifyMusic spotifyMusic = spotifyMusicRepository.findBySpotifyId(spotifyItemResponse.id()).orElseThrow(() -> {
                throw new BusinessLogicException(ErrorCode.NOT_FOUND);
            });
            log.setSpotifyMusic(spotifyMusic);

        }
        // 로그 with 커스텀
        if (requestDto.musicType().equals(MusicType.CUSTOM)) {

            log.setSpotifyMusic(null);
            CustomMusicRequestDto customMusicRequestDto = new CustomMusicRequestDto(requestDto.name(),
                requestDto.imageUrl(), requestDto.artist());

            // TODO : 로그 수정 할때도 커스텀 음악은 삭제안해도 되는지...?
            CustomMusicResponseDto customMusicResponseDto = customMusicService.saveCustomMusic(customMusicRequestDto);
            CustomMusic customMusic = customMusicRepository.findById(customMusicResponseDto.getId()).orElseThrow(() -> {
                throw new BusinessLogicException(ErrorCode.NOT_FOUND);
            });
            log.setCustomMusic(customMusic);

        }

        // 기존의 이미지 정보 삭제 후 다시 추가
        imageInfoService.deleteImages(log.getId());
        imageInfoService.uploadImages(log.getId(), multipartFile);

    }

    // 삭제 서비스 (로그 and 이미지)
    @Transactional
    public void deleteLog(long userId, long logId) {

        Log log = logRepository.findByIdAndUserId(logId, userId).orElseThrow(() -> {
            throw new BusinessLogicException(ErrorCode.NOT_FOUND);
        });

        // 이미지 정보 삭제
        imageInfoService.deleteImages(log.getId());

        // 로그 삭제
        logRepository.delete(log);

    }

    // 기록 보기 1페이지 - MUSIC
    @Transactional(readOnly = true)
    public void getLogMusic(long userId, long logId) {

        //음악 -> 앨범커버이미지, 아티스트, 제목
        //로그 -> 한줄평, 카테고리, 위치, 날짜
        Log log = logRepository.findByIdAndUserId(logId, userId).orElseThrow(() -> {
            throw new BusinessLogicException(ErrorCode.NOT_FOUND);
        });


    }

    // 기록 보기 2페이지 - RECORD
    @Transactional(readOnly = true)
    public GetLogRecordResponseDto getLogRecord(long userId, long logId) {

        Log log = logRepository.findByIdAndUserId(logId, userId).orElseThrow(() -> {
            throw new BusinessLogicException(ErrorCode.NOT_FOUND);
        });

        List<String> fileUrlList = imageInfoRepository.findAllByLogIdOrderByCreatedDateAsc(logId);

        return new GetLogRecordResponseDto(log.getRecord(), fileUrlList);
    }

    // 기록 보기 3페이지 - PLAY
    @Transactional(readOnly = true)
    public GetLogPlayResponseDto getLogPlay(long userId, long logId) {

        Log log = logRepository.findByIdAndUserId(logId, userId).orElseThrow(() -> {
            throw new BusinessLogicException(ErrorCode.NOT_FOUND);
        });

        if (log.getYoutubeInfo() == null) {
            return new GetLogPlayResponseDto(null, null, null, null);
        } else {
            return new GetLogPlayResponseDto(log.getYoutubeInfo().getTitle(), log.getYoutubeInfo().getChannelTitle(),
                log.getYoutubeInfo().getPublishedAt(), log.getYoutubeInfo().getVideoId());
        }

    }

}
