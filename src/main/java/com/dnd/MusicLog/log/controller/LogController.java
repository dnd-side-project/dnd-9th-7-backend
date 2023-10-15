package com.dnd.MusicLog.log.controller;

import com.dnd.MusicLog.global.auth.PrincipalId;
import com.dnd.MusicLog.global.common.BaseController;
import com.dnd.MusicLog.global.common.BaseResponse;
import com.dnd.MusicLog.global.common.SuccessResponse;
import com.dnd.MusicLog.log.dto.GetFullLogResponseDto;
import com.dnd.MusicLog.log.dto.GetLogMusicResponseDto;
import com.dnd.MusicLog.log.dto.GetLogPlayResponseDto;
import com.dnd.MusicLog.log.dto.GetLogRecordResponseDto;
import com.dnd.MusicLog.log.dto.GetTempLogMusicInfoListResponseDto;
import com.dnd.MusicLog.log.dto.SaveLogRequestDto;
import com.dnd.MusicLog.log.dto.SaveLogResponseDto;
import com.dnd.MusicLog.log.service.LogService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/api/log")
@RestController
public class LogController extends BaseController {

    private final LogService logService;

    @PostMapping("")
    public ResponseEntity<BaseResponse<SaveLogResponseDto>> saveLog(
        @PrincipalId long userId,
        @RequestPart("images") List<MultipartFile> multipartFile,
        @RequestPart("saveLogRequestDto") SaveLogRequestDto requestDto) {
        SaveLogResponseDto responseDto = logService.saveLog(userId, requestDto, multipartFile);
        return createBaseResponse(HttpStatus.CREATED, "로그 저장 완료", responseDto);

    }

    @PutMapping("/{logId}")
    public ResponseEntity<SuccessResponse> updateLog(
        @PrincipalId long userId,
        @RequestPart("images") List<MultipartFile> multipartFile,
        @RequestPart("saveLogRequestDto") SaveLogRequestDto requestDto,
        @PathVariable(name = "logId") long logId) {
        logService.updateLog(userId, logId, requestDto, multipartFile);
        return createSuccessResponse(HttpStatus.OK, "로그 수정 완료");
    }

    @DeleteMapping("/{logId}")
    public ResponseEntity<SuccessResponse> deleteLog(
        @PrincipalId long userId,
        @PathVariable(name = "logId") long logId) {
        logService.deleteLog(userId, logId);
        return createSuccessResponse(HttpStatus.OK, "로그 삭제 완료");
    }

    @GetMapping("/{logId}/music")
    public ResponseEntity<BaseResponse<GetLogMusicResponseDto>> getLogMusic(
        @PrincipalId long userId,
        @PathVariable(name = "logId") long logId) {
        GetLogMusicResponseDto responseDto = logService.getLogMusic(userId, logId);
        return createBaseResponse(HttpStatus.OK, "로그(MUSIC 정보) 조회 완료", responseDto);
    }

    @GetMapping("/{logId}/record")
    public ResponseEntity<BaseResponse<GetLogRecordResponseDto>> getLogRecord(
        @PrincipalId long userId,
        @PathVariable(name = "logId") long logId) {
        GetLogRecordResponseDto responseDto = logService.getLogRecord(userId, logId);
        return createBaseResponse(HttpStatus.OK, "로그(RECORD 정보) 조회 완료", responseDto);
    }

    @GetMapping("/{logId}/play")
    public ResponseEntity<BaseResponse<GetLogPlayResponseDto>> getLogPlay(
        @PrincipalId long userId,
        @PathVariable(name = "logId") long logId) {
        GetLogPlayResponseDto responseDto = logService.getLogPlay(userId, logId);
        return createBaseResponse(HttpStatus.OK, "로그(PLAY 정보) 조회 완료", responseDto);
    }

    @GetMapping("/{logId}")
    public ResponseEntity<BaseResponse<GetFullLogResponseDto>> getFullLog(
        @PrincipalId long userId,
        @PathVariable(name = "logId") long logId) {
        GetFullLogResponseDto responseDto = logService.getFullLog(userId, logId);
        return createBaseResponse(HttpStatus.OK, "로그 전체 정보 조회 완료", responseDto);
    }

    @GetMapping("/temp")
    public ResponseEntity<BaseResponse<GetTempLogMusicInfoListResponseDto>> getTempLogs(
        @PrincipalId long userId) {
        GetTempLogMusicInfoListResponseDto responseDto = logService.getTempLogs(userId);
        return createBaseResponse(HttpStatus.OK, "임시저장 로그 정보 조회 완료", responseDto);
    }
}
