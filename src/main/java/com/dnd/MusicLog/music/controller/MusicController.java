package com.dnd.MusicLog.music.controller;

import com.dnd.MusicLog.global.common.BaseController;
import com.dnd.MusicLog.global.common.BaseResponse;
import com.dnd.MusicLog.global.jwt.util.JwtTokenProvider;
import com.dnd.MusicLog.music.dto.SaveCustomMusicRequestDto;
import com.dnd.MusicLog.music.dto.SaveCustomMusicResponseDto;
import com.dnd.MusicLog.music.dto.SearchCustomMusicResponseDto;
import com.dnd.MusicLog.music.dto.SpotifyTrackResponseDto;
import com.dnd.MusicLog.music.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/music")
@RestController
public class MusicController extends BaseController {

    private final JwtTokenProvider jwtTokenProvider;
    private final MusicService musicService;

    @GetMapping("/spotify")
    public SpotifyTrackResponseDto searchSpotifyMusic(
        @RequestParam("query") String query,
        @RequestParam("offset") int offset) {
        return musicService.searchSpotifyMusic(query, offset);
    }

    @GetMapping("/custom")
    public ResponseEntity<BaseResponse<SearchCustomMusicResponseDto>> searchCustomMusic(
        @RequestHeader(name = "Authorization") String token,
        @RequestParam(value = "query") String query,
        @RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
        @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        String subject = jwtTokenProvider.extractAccessTokenSubject(token);
        long userId = Long.parseLong(subject);

        SearchCustomMusicResponseDto response =
            musicService.searchCustomMusic(userId, query, offset, size);

        return createBaseResponse(HttpStatus.OK, "커스텀 음악 검색 성공", response);
    }

    @PostMapping("/custom")
    public ResponseEntity<BaseResponse<SaveCustomMusicResponseDto>> saveCustomMusic(
        @RequestHeader(name = "Authorization") String token,
        @RequestBody SaveCustomMusicRequestDto saveCustomMusicRequestDto) {
        String subject = jwtTokenProvider.extractAccessTokenSubject(token);
        long userId = Long.parseLong(subject);

        SaveCustomMusicResponseDto responseDto = musicService.saveCustomMusic(userId, saveCustomMusicRequestDto);

        return createBaseResponse(HttpStatus.OK, "음악 정보 저장 완료", responseDto);
    }
}
