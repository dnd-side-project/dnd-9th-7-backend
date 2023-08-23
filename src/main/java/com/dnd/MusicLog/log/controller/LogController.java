package com.dnd.MusicLog.log.controller;

import com.dnd.MusicLog.global.common.BaseController;
import com.dnd.MusicLog.global.common.BaseResponse;
import com.dnd.MusicLog.global.common.SuccessResponse;
import com.dnd.MusicLog.global.jwt.util.JwtTokenProvider;
import com.dnd.MusicLog.imageinfo.dto.FileNamesResponseDto;
import com.dnd.MusicLog.imageinfo.service.ImageInfoService;
import com.dnd.MusicLog.log.dto.GetLogRecordResponseDto;
import com.dnd.MusicLog.log.dto.SaveLogRequestDto;
import com.dnd.MusicLog.log.service.LogService;
import com.dnd.MusicLog.music.dto.CustomMusicItem;
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

    //TODO : SaveLogRequestDto에 스포티파이 음악 저장하는데 필요한 프로퍼티 추가필요.
    @PostMapping("")
    public ResponseEntity<SuccessResponse> saveLog(@RequestHeader(name = "Authorization") String bearerToken,
                                                              @RequestPart("images") List<MultipartFile> multipartFile,
                                                              @RequestBody SaveLogRequestDto requestDto) {

        String subject = jwtTokenProvider.extractAccessTokenSubject(bearerToken);
        long userId = Long.parseLong(subject);

        logService.saveLog(userId, requestDto, multipartFile);
        return createSuccessResponse(HttpStatus.CREATED, "로그 저장 완료");

    }

    @GetMapping("/{logId}/record")
    public ResponseEntity<BaseResponse<GetLogRecordResponseDto>> getLogRecord(@RequestHeader(name = "Authorization") String bearerToken,
                                                                 @PathVariable(name = "logId") long logId) {

        String subject = jwtTokenProvider.extractAccessTokenSubject(bearerToken);
        long userId = Long.parseLong(subject);

        GetLogRecordResponseDto responseDto = logService.getLogRecord(userId, logId);
        return createBaseResponse(HttpStatus.OK, "로그(RECORD 정보) 조회 완료",responseDto);

    }
}
