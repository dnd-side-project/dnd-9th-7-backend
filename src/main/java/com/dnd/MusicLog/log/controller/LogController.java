package com.dnd.MusicLog.log.controller;

import com.dnd.MusicLog.global.common.BaseController;
import com.dnd.MusicLog.global.common.BaseResponse;
import com.dnd.MusicLog.global.common.SuccessResponse;
import com.dnd.MusicLog.global.jwt.util.JwtTokenProvider;
import com.dnd.MusicLog.log.dto.*;
import com.dnd.MusicLog.log.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/log")
@RestController
public class LogController extends BaseController {

    private final JwtTokenProvider jwtTokenProvider;
    private final LogService logService;

    @PostMapping("")
    public ResponseEntity<BaseResponse<SaveLogResponseDto>> saveLog(@RequestHeader(name = "Authorization") String bearerToken,
                                                              @RequestPart("images") List<MultipartFile> multipartFile,
                                                              @RequestPart("saveLogRequestDto") SaveLogRequestDto requestDto) {

        String subject = jwtTokenProvider.extractAccessTokenSubject(bearerToken);
        long userId = Long.parseLong(subject);

        SaveLogResponseDto responseDto = logService.saveLog(userId, requestDto, multipartFile);
        return createBaseResponse(HttpStatus.CREATED, "로그 저장 완료", responseDto);

    }

    @PutMapping("/{logId}")
    public ResponseEntity<SuccessResponse> updateLog(@RequestHeader(name = "Authorization") String bearerToken,
                                                     @RequestPart("images") List<MultipartFile> multipartFile,
                                                     @RequestPart("saveLogRequestDto") SaveLogRequestDto requestDto,
                                                     @PathVariable(name = "logId") long logId) {

        String subject = jwtTokenProvider.extractAccessTokenSubject(bearerToken);
        long userId = Long.parseLong(subject);

        logService.updateLog(userId, logId, requestDto, multipartFile);
        return createSuccessResponse(HttpStatus.OK, "로그 수정 완료");

    }

    @DeleteMapping("/{logId}")
    public ResponseEntity<SuccessResponse> deleteLog(@RequestHeader(name = "Authorization") String bearerToken,
                                                     @PathVariable(name = "logId") long logId) {

        String subject = jwtTokenProvider.extractAccessTokenSubject(bearerToken);
        long userId = Long.parseLong(subject);

        logService.deleteLog(userId, logId);
        return createSuccessResponse(HttpStatus.OK, "로그 삭제 완료");

    }

    @GetMapping("/{logId}/music")
    public ResponseEntity<BaseResponse<GetLogMusicResponseDto>> getLogMusic(@RequestHeader(name = "Authorization") String bearerToken,
                                                                              @PathVariable(name = "logId") long logId) {

        String subject = jwtTokenProvider.extractAccessTokenSubject(bearerToken);
        long userId = Long.parseLong(subject);

        GetLogMusicResponseDto responseDto = logService.getLogMusic(userId, logId);
        return createBaseResponse(HttpStatus.OK, "로그(MUSIC 정보) 조회 완료", responseDto);

    }

    @GetMapping("/{logId}/record")
    public ResponseEntity<BaseResponse<GetLogRecordResponseDto>> getLogRecord(@RequestHeader(name = "Authorization") String bearerToken,
                                                                 @PathVariable(name = "logId") long logId) {

        String subject = jwtTokenProvider.extractAccessTokenSubject(bearerToken);
        long userId = Long.parseLong(subject);

        GetLogRecordResponseDto responseDto = logService.getLogRecord(userId, logId);
        return createBaseResponse(HttpStatus.OK, "로그(RECORD 정보) 조회 완료", responseDto);

    }

    @GetMapping("/{logId}/play")
    public ResponseEntity<BaseResponse<GetLogPlayResponseDto>> getLogPlay(@RequestHeader(name = "Authorization") String bearerToken,
                                                                              @PathVariable(name = "logId") long logId) {

        String subject = jwtTokenProvider.extractAccessTokenSubject(bearerToken);
        long userId = Long.parseLong(subject);

        GetLogPlayResponseDto responseDto = logService.getLogPlay(userId, logId);
        return createBaseResponse(HttpStatus.OK, "로그(PLAY 정보) 조회 완료", responseDto);

    }

    @GetMapping("/{logId}")
    public ResponseEntity<BaseResponse<GetFullLogResponseDto>> getFullLog(@RequestHeader(name = "Authorization") String bearerToken,
                                                                            @PathVariable(name = "logId") long logId) {

        String subject = jwtTokenProvider.extractAccessTokenSubject(bearerToken);
        long userId = Long.parseLong(subject);

        GetFullLogResponseDto responseDto = logService.getFullLog(userId, logId);
        return createBaseResponse(HttpStatus.OK, "로그 전체 정보 조회 완료", responseDto);

    }

    @GetMapping("/temp")
    public ResponseEntity<BaseResponse<GetTempLogMusicInfoListResponseDto>> getTempLogs(@RequestHeader(name = "Authorization") String bearerToken) {

        String subject = jwtTokenProvider.extractAccessTokenSubject(bearerToken);
        long userId = Long.parseLong(subject);

        GetTempLogMusicInfoListResponseDto responseDto = logService.getTempLogs(userId);
        return createBaseResponse(HttpStatus.OK, "임시저장 로그 정보 조회 완료", responseDto);

    }

}
