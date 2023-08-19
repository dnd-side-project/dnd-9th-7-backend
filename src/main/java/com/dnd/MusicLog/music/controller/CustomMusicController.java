package com.dnd.MusicLog.music.controller;

import com.dnd.MusicLog.global.common.BaseController;
import com.dnd.MusicLog.global.common.BaseResponse;
import com.dnd.MusicLog.global.jwt.util.JwtTokenProvider;
import com.dnd.MusicLog.music.dto.CustomMusicItem;
import com.dnd.MusicLog.music.dto.CustomMusicRequestDto;
import com.dnd.MusicLog.music.dto.CustomMusicResponseDto;
import com.dnd.MusicLog.music.dto.SearchCustomMusicResponseDto;
import com.dnd.MusicLog.music.service.CustomMusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/music/custom")
@RestController
public class CustomMusicController extends BaseController {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomMusicService customMusicService;

    @GetMapping("")
    public ResponseEntity<BaseResponse<SearchCustomMusicResponseDto>> searchCustomMusic(
        @RequestHeader(name = "Authorization") String token,
        @RequestParam(value = "query") String query,
        @RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
        @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        String subject = jwtTokenProvider.extractAccessTokenSubject(token);
        long userId = Long.parseLong(subject);

        SearchCustomMusicResponseDto response =
            customMusicService.searchCustomMusic(userId, query, offset, size);

        return createBaseResponse(HttpStatus.OK, "커스텀 음악 검색 성공", response);
    }

    @GetMapping("/{customMusicId}")
    public ResponseEntity<BaseResponse<CustomMusicItem>> searchCustomMusic(
        @RequestHeader(name = "Authorization") String token,
        @PathVariable(name = "customMusicId") long customMusicId) {
        String subject = jwtTokenProvider.extractAccessTokenSubject(token);
        long userId = Long.parseLong(subject);

        CustomMusicItem response = customMusicService.searchCustomMusic(userId, customMusicId);

        return createBaseResponse(HttpStatus.OK, "커스텀 음악 검색 성공", response);
    }

    @PostMapping("")
    public ResponseEntity<BaseResponse<CustomMusicResponseDto>> saveCustomMusic(
        @RequestHeader(name = "Authorization") String token,
        @RequestBody CustomMusicRequestDto customMusicRequestDto) {
        String subject = jwtTokenProvider.extractAccessTokenSubject(token);
        long userId = Long.parseLong(subject);

        CustomMusicResponseDto responseDto = customMusicService.saveCustomMusic(userId, customMusicRequestDto);

        return createBaseResponse(HttpStatus.OK, "커스텀 음악 저장 완료", responseDto);
    }

    @PutMapping("/{customMusicId}")
    public ResponseEntity<BaseResponse<CustomMusicResponseDto>> updateCustomMusic(
        @RequestHeader(name = "Authorization") String token,
        @PathVariable(name = "customMusicId") long customMusicId,
        @RequestBody CustomMusicRequestDto customMusicRequestDto) {
        String subject = jwtTokenProvider.extractAccessTokenSubject(token);
        long userId = Long.parseLong(subject);

        CustomMusicResponseDto response =
            customMusicService.updateCustomMusic(userId, customMusicId, customMusicRequestDto);

        return createBaseResponse(HttpStatus.OK, "커스텀 음악 수정 완료", response);
    }

    @DeleteMapping("/{customMusicId}")
    public ResponseEntity<BaseResponse<CustomMusicResponseDto>> deleteCustomMusic(
        @RequestHeader(name = "Authorization") String token,
        @PathVariable(name = "customMusicId") long customMusicId) {
        String subject = jwtTokenProvider.extractAccessTokenSubject(token);
        long userId = Long.parseLong(subject);

        CustomMusicResponseDto response = customMusicService.deleteCustomMusic(userId, customMusicId);

        return createBaseResponse(HttpStatus.OK, "커스텀 음악 삭제 완료", response);
    }
}
