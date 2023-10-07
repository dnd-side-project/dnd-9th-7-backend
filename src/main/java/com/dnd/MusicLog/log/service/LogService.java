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
import com.dnd.MusicLog.music.dto.SpotifyArtistResponse;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    public GetLogMusicResponseDto getLogMusic(long userId, long logId) {

        Log log = logRepository.findByIdAndUserId(logId, userId).orElseThrow(() -> {
            throw new BusinessLogicException(ErrorCode.NOT_FOUND);
        });

        List<String> artistList = new ArrayList<>();

        if (log.getMusicType() == MusicType.SPOTIFY) {

            SpotifyItemResponse spotifyItemResponse = spotifyMusicService.searchSpotifyTrack(log.getSpotifyMusic().getSpotifyId());

            List<SpotifyArtistResponse> artists = spotifyItemResponse.artists();
            for (SpotifyArtistResponse artist: artists) {
                artistList.add(artist.name());
            }

            return new GetLogMusicResponseDto(log.getSpotifyMusic().getAlbum().getImageUrl(), artistList,
                log.getSpotifyMusic().getName(), log.getReview(), log.getFeeling().name(), log.getTime().name(), log.getWeather().name(),
                log.getLocation(), log.getDate()
            );
        }

        if (log.getMusicType() == MusicType.CUSTOM) {

            artistList.add(log.getCustomMusic().getArtist());
            return new GetLogMusicResponseDto(log.getCustomMusic().getImageUrl(), artistList,
                log.getCustomMusic().getName(), log.getReview(), log.getFeeling().name(), log.getTime().name(), log.getWeather().name(),
                log.getLocation(), log.getDate()
            );
        }

        throw new BusinessLogicException(ErrorCode.SERVER_ERROR);

    }

    // 기록 보기 2페이지 - RECORD
    @Transactional(readOnly = true)
    public GetLogRecordResponseDto getLogRecord(long userId, long logId) {

        Log log = logRepository.findByIdAndUserId(logId, userId).orElseThrow(() -> {
            throw new BusinessLogicException(ErrorCode.NOT_FOUND);
        });

        List<String> fileUrlList = imageInfoRepository.findAllImageUrlByLogIdOrderByCreatedDateAsc(logId);

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

    // 로그 기록 전체 조회 = 수정 페이지
    @Transactional(readOnly = true)
    public GetFullLogResponseDto getFullLog(long userId, long logId) {

        Log log = logRepository.findByIdAndUserId(logId, userId).orElseThrow(() -> {
            throw new BusinessLogicException(ErrorCode.NOT_FOUND);
        });

        // 기록 보기 1페이지 정보 - MUSIC
        List<String> artistList = new ArrayList<>();
        GetLogMusicResponseDto logMusicResponseDto = null;
        GetLogRecordResponseDto logRecordResponseDto;
        GetLogPlayResponseDto logPlayResponseDto;

        if (log.getMusicType() == MusicType.SPOTIFY) {

            SpotifyItemResponse spotifyItemResponse = spotifyMusicService.searchSpotifyTrack(log.getSpotifyMusic().getSpotifyId());

            List<SpotifyArtistResponse> artists = spotifyItemResponse.artists();
            for (SpotifyArtistResponse artist: artists) {
                artistList.add(artist.name());
            }

            logMusicResponseDto = new GetLogMusicResponseDto(log.getSpotifyMusic().getAlbum().getImageUrl(), artistList,
                log.getSpotifyMusic().getName(), log.getReview(), log.getFeeling().name(), log.getTime().name(), log.getWeather().name(),
                log.getLocation(), log.getDate()
            );
        }

        if (log.getMusicType() == MusicType.CUSTOM) {

            artistList.add(log.getCustomMusic().getArtist());
            logMusicResponseDto = new GetLogMusicResponseDto(log.getCustomMusic().getImageUrl(), artistList,
                log.getCustomMusic().getName(), log.getReview(), log.getFeeling().name(), log.getTime().name(), log.getWeather().name(),
                log.getLocation(), log.getDate()
            );
        }

        // 기록 보기 2페이지 정보 - RECORD
        List<String> fileUrlList = imageInfoRepository.findAllImageUrlByLogIdOrderByCreatedDateAsc(logId);

        logRecordResponseDto = new GetLogRecordResponseDto(log.getRecord(), fileUrlList);

        // 기록 보기 3페이지 정보 - PLAY
        if (log.getYoutubeInfo() == null) {
            logPlayResponseDto = new GetLogPlayResponseDto(null, null, null, null);
        } else {
            logPlayResponseDto = new GetLogPlayResponseDto(log.getYoutubeInfo().getTitle(), log.getYoutubeInfo().getChannelTitle(),
                log.getYoutubeInfo().getPublishedAt(), log.getYoutubeInfo().getVideoId());
        }

        return new GetFullLogResponseDto(logMusicResponseDto, logRecordResponseDto, logPlayResponseDto);

    }

    // 임시저장 기록 리스트 조회
    @Transactional(readOnly = true)
    public GetTempLogMusicInfoListResponseDto getTempLogs(long userId) {
        List<Log> logList = logRepository.findByUserIdAndTemp(userId);

        TempLogMusicInfo tempLogMusicInfo = null;
        List<TempLogMusicInfo> tempLogMusicInfos = new ArrayList<>();

        for (Log log : logList) {

            List<String> artistList = new ArrayList<>();

            if (log.getMusicType() == MusicType.SPOTIFY) {

                SpotifyItemResponse spotifyItemResponse = spotifyMusicService.searchSpotifyTrack(log.getSpotifyMusic().getSpotifyId());

                List<SpotifyArtistResponse> artists = spotifyItemResponse.artists();
                for (SpotifyArtistResponse artist: artists) {
                    artistList.add(artist.name());
                }

                tempLogMusicInfo = new TempLogMusicInfo(log.getId(), log.getSpotifyMusic().getAlbum().getImageUrl(),
                    log.getSpotifyMusic().getName(), artistList);

            }

            if (log.getMusicType() == MusicType.CUSTOM) {

                artistList.add(log.getCustomMusic().getArtist());

                tempLogMusicInfo = new TempLogMusicInfo(log.getId(), log.getCustomMusic().getImageUrl(),
                    log.getCustomMusic().getName(), artistList);
            }

            tempLogMusicInfos.add(tempLogMusicInfo);

        }

        return new GetTempLogMusicInfoListResponseDto(tempLogMusicInfos);
    }

    // 캘린더 월별 데이터 제공
    @Transactional(readOnly = true)
    public GetMonthCalenderInfoResponseDto getMonthCalenderInfo(long userId, String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate thisMonthlocalDate = LocalDate.parse(date, formatter);

        MonthLogInfo lastMonthLogInfo = generateMonthLogInfo(userId, thisMonthlocalDate.minusMonths(1));
        MonthLogInfo thisMonthLogInfo = generateMonthLogInfo(userId, thisMonthlocalDate);
        MonthLogInfo nextMonthLogInfo = generateMonthLogInfo(userId, thisMonthlocalDate.plusMonths(1));

        return new GetMonthCalenderInfoResponseDto(lastMonthLogInfo, thisMonthLogInfo, nextMonthLogInfo);
    }

    // 캘린더 일별 데이터 제공
    @Transactional(readOnly = true)
    public List<GetDayCalenderInfoResponseDto> getDayCalenderInfo(long userId, String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate localDate = LocalDate.parse(date, formatter);

        List<Log> logList = logRepository.findAllByUserIdAndDay(userId, localDate);

        List<GetDayCalenderInfoResponseDto> getDayCalenderInfos = new ArrayList<>();

        for (Log log : logList) {

            List<String> artistList = new ArrayList<>();

            if (log.getMusicType() == MusicType.SPOTIFY) {

                SpotifyItemResponse spotifyItemResponse = spotifyMusicService.searchSpotifyTrack(log.getSpotifyMusic().getSpotifyId());

                List<SpotifyArtistResponse> artists = spotifyItemResponse.artists();
                for (SpotifyArtistResponse artist: artists) {
                    artistList.add(artist.name());
                }

                GetDayCalenderInfoResponseDto getDayCalenderInfoResponseDto = new GetDayCalenderInfoResponseDto(log.getId(),
                    log.getSpotifyMusic().getAlbum().getImageUrl(), artistList, log.getSpotifyMusic().getName(), log.isRepresentation());

                getDayCalenderInfos.add(getDayCalenderInfoResponseDto);
            }

            if (log.getMusicType() == MusicType.CUSTOM) {

                artistList.add(log.getCustomMusic().getArtist());
                GetDayCalenderInfoResponseDto getDayCalenderInfoResponseDto = new GetDayCalenderInfoResponseDto(log.getId(),
                    log.getCustomMusic().getImageUrl(), artistList, log.getCustomMusic().getName(), log.isRepresentation());

                getDayCalenderInfos.add(getDayCalenderInfoResponseDto);

            }
        }

        return getDayCalenderInfos;
    }

    private MonthLogInfo generateMonthLogInfo(long userId, LocalDate localDate) {

        List<CalenderAlbumImageInfo> calenderAlbumImageInfoList = new ArrayList<>();

        Object[] logCountInfosList = logRepository.findDayCountAndRecordCountInfo(userId, localDate);

        Object[] results= (Object[]) logCountInfosList[0];
        CalenderLogCountInfo calenderlogCountinfo =
            new CalenderLogCountInfo(((Long) results[0]), ((Long) results[1]));

        List<Log> logList= logRepository.findAllByUserIdAndMonth(userId , localDate);

        for (Log log : logList ) {
            if (log.getMusicType() == MusicType.SPOTIFY) {
                calenderAlbumImageInfoList.add(new CalenderAlbumImageInfo(log.getDate(),
                    log.getSpotifyMusic().getAlbum().getImageUrl()));
            }

            if (log.getMusicType() == MusicType.CUSTOM){
                calenderAlbumImageInfoList.add(new CalenderAlbumImageInfo(log.getDate(),
                    log.getCustomMusic().getImageUrl()));
            }
        }

        return new MonthLogInfo(localDate.getYear(), localDate.getMonthValue(), calenderlogCountinfo.dayCount(),
            calenderlogCountinfo.recordCount(), calenderAlbumImageInfoList);
    }
}
