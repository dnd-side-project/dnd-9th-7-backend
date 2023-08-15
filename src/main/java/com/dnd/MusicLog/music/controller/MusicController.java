package com.dnd.MusicLog.music.controller;

import com.dnd.MusicLog.global.common.BaseController;
import com.dnd.MusicLog.global.common.BaseResponse;
import com.dnd.MusicLog.global.jwt.util.JwtTokenProvider;
import com.dnd.MusicLog.music.dto.SaveMusicRequestDto;
import com.dnd.MusicLog.music.dto.SaveMusicRequestDto.AlbumRequestDto;
import com.dnd.MusicLog.music.dto.SaveMusicRequestDto.ArtistRequestDto;
import com.dnd.MusicLog.music.dto.SaveMusicRequestDto.MusicRequestDto;
import com.dnd.MusicLog.music.dto.SaveMusicResponseDto;
import com.dnd.MusicLog.music.dto.SpotifyTrackResponseDto;
import com.dnd.MusicLog.music.service.MusicService;
import java.util.ArrayList;
import java.util.List;
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

        // 필수 값으로 null validation 진행하지 않음
        MusicRequestDto music = saveMusicRequestDto.music();

        List<ArtistRequestDto> artists = saveMusicRequestDto.artists();
        if (artists == null || artists.isEmpty()) {
            artists = new ArrayList<>();
        }

        AlbumRequestDto album = saveMusicRequestDto.album();
        if (album == null) {
            album = new AlbumRequestDto(null, null, null, false, null);
        }

        SaveMusicResponseDto responseDto = musicService.saveMusic(
            userId,
            music,
            artists,
            album);

        return createBaseResponse(HttpStatus.OK, "음악 정보 저장 완료", responseDto);
    }
}
