package com.dnd.MusicLog.music.controller;

import com.dnd.MusicLog.global.auth.PrincipalId;
import com.dnd.MusicLog.global.common.BaseController;
import com.dnd.MusicLog.global.common.BaseResponse;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/music/custom")
@RestController
public class CustomMusicController extends BaseController {

    private final CustomMusicService customMusicService;

    @GetMapping("")
    public ResponseEntity<BaseResponse<SearchCustomMusicResponseDto>> searchCustomMusic(
        @PrincipalId long userId,
        @RequestParam(value = "query") String query,
        @RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
        @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        SearchCustomMusicResponseDto response =
            customMusicService.searchCustomMusic(query, offset, size);

        return createBaseResponse(HttpStatus.OK, "커스텀 음악 검색 성공", response);
    }

    @GetMapping("/{customMusicId}")
    public ResponseEntity<BaseResponse<CustomMusicItem>> searchCustomMusic(
        @PrincipalId long userId,
        @PathVariable(name = "customMusicId") long customMusicId) {
        CustomMusicItem response = customMusicService.searchCustomMusic(customMusicId);

        return createBaseResponse(HttpStatus.OK, "커스텀 음악 검색 성공", response);
    }

    @PostMapping("")
    public ResponseEntity<BaseResponse<CustomMusicResponseDto>> saveCustomMusic(
        @PrincipalId long userId,
        @RequestBody CustomMusicRequestDto customMusicRequestDto) {
        CustomMusicResponseDto responseDto = customMusicService.saveCustomMusic(customMusicRequestDto);

        return createBaseResponse(HttpStatus.OK, "커스텀 음악 저장 완료", responseDto);
    }

    @PutMapping("/{customMusicId}")
    public ResponseEntity<BaseResponse<CustomMusicResponseDto>> updateCustomMusic(
        @PrincipalId long userId,
        @PathVariable(name = "customMusicId") long customMusicId,
        @RequestBody CustomMusicRequestDto customMusicRequestDto) {
        CustomMusicResponseDto response =
            customMusicService.updateCustomMusic(customMusicId, customMusicRequestDto);

        return createBaseResponse(HttpStatus.OK, "커스텀 음악 수정 완료", response);
    }

    @DeleteMapping("/{customMusicId}")
    public ResponseEntity<BaseResponse<CustomMusicResponseDto>> deleteCustomMusic(
        @PrincipalId long userId,
        @PathVariable(name = "customMusicId") long customMusicId) {
        CustomMusicResponseDto response = customMusicService.deleteCustomMusic(customMusicId);

        return createBaseResponse(HttpStatus.OK, "커스텀 음악 삭제 완료", response);
    }
}
