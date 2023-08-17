package com.dnd.MusicLog.music.controller;

import com.dnd.MusicLog.global.common.BaseController;
import com.dnd.MusicLog.global.common.BaseResponse;
import com.dnd.MusicLog.global.jwt.util.JwtTokenProvider;
import com.dnd.MusicLog.music.dto.SaveMusicRequestDto;
import com.dnd.MusicLog.music.dto.SaveMusicResponseDto;
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

    @GetMapping("")
    public SpotifyTrackResponseDto searchMusic(
        @RequestParam("query") String query,
        @RequestParam("offset") int offset) {
        return musicService.searchMusic(query, offset);
    }

    @PostMapping("")
    public ResponseEntity<BaseResponse<SaveMusicResponseDto>> saveMusic(
        @RequestHeader(name = "Authorization") String token,
        @RequestBody SaveMusicRequestDto saveMusicRequestDto) {
        String subject = jwtTokenProvider.extractAccessTokenSubject(token);
        long userId = Long.parseLong(subject);

        SaveMusicResponseDto responseDto = musicService.saveMusic(userId, saveMusicRequestDto);

        return createBaseResponse(HttpStatus.OK, "음악 정보 저장 완료", responseDto);
    }
}
