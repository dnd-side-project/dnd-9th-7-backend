package com.dnd.MusicLog.music.controller;

import com.dnd.MusicLog.global.common.BaseController;
import com.dnd.MusicLog.global.jwt.util.JwtTokenProvider;
import com.dnd.MusicLog.music.dto.SpotifyItemResponse;
import com.dnd.MusicLog.music.dto.SpotifyTrackResponseDto;
import com.dnd.MusicLog.music.service.SpotifyMusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/music/spotify")
@RestController
public class SpotifyMusicController extends BaseController {

    private final JwtTokenProvider jwtTokenProvider;
    private final SpotifyMusicService spotifyMusicService;

    @GetMapping("")
    public SpotifyTrackResponseDto searchSpotifyMusic(
        @RequestHeader("Authorization") String token,
        @RequestParam("query") String query,
        @RequestParam(value = "offset", defaultValue = "0") int offset) {
        jwtTokenProvider.extractAccessTokenSubject(token);

        return spotifyMusicService.searchSpotifyTrackList(query, offset);
    }

    @GetMapping("/{spotifyId}")
    public SpotifyItemResponse getSpotifyMusic(
        @RequestHeader("Authorization") String token,
        @PathVariable(name = "spotifyId") String spotifyId) {
        jwtTokenProvider.extractAccessTokenSubject(token);

        return spotifyMusicService.searchSpotifyTrack(spotifyId);
    }
}
