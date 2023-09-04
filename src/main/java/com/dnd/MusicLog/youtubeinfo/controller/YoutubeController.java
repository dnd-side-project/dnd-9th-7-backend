package com.dnd.MusicLog.youtubeinfo.controller;

import com.dnd.MusicLog.global.common.BaseController;
import com.dnd.MusicLog.global.common.BaseResponse;
import com.dnd.MusicLog.global.jwt.util.JwtTokenProvider;
import com.dnd.MusicLog.log.dto.YoutubeVideoListResponseDto;
import com.dnd.MusicLog.youtubeinfo.service.SearchYoutubeVideosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class YoutubeController extends BaseController {

    private final JwtTokenProvider jwtTokenProvider;
    private final SearchYoutubeVideosService searchYoutubeVideosService;

    @GetMapping("/api/youtube/video")
    public ResponseEntity<BaseResponse<YoutubeVideoListResponseDto>> searchVideos(@RequestHeader(name = "Authorization") String bearerToken,
                                                                                  @RequestParam String query,
                                                                                  @RequestParam(required = false) String pageToken) {

        jwtTokenProvider.extractAccessTokenSubject(bearerToken);
        YoutubeVideoListResponseDto responseDto = searchYoutubeVideosService.searchYoutubeVideos(query, pageToken);
        return createBaseResponse(HttpStatus.OK, "유튜브 영상 조회 완료", responseDto);

    }
}
