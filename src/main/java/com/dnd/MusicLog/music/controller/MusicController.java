package com.dnd.MusicLog.music.controller;

import com.dnd.MusicLog.music.dto.SpotifyTrackResponseDto;
import com.dnd.MusicLog.music.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/music")
@RestController
public class MusicController {

    private final MusicService musicService;

    @GetMapping("")
    public SpotifyTrackResponseDto searchMusic(
        @RequestParam("query") String query,
        @RequestParam("offset") int offset) {
        return musicService.searchMusic(query, offset);
    }
}
