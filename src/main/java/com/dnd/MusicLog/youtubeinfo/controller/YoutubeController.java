package com.dnd.MusicLog.youtubeinfo.controller;

import com.dnd.MusicLog.global.auth.PrincipalId;
import com.dnd.MusicLog.global.common.BaseController;
import com.dnd.MusicLog.global.common.BaseResponse;
import com.dnd.MusicLog.log.dto.YoutubeVideoListResponseDto;
import com.dnd.MusicLog.youtubeinfo.service.SearchYoutubeVideosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/youtube")
@RestController
public class YoutubeController extends BaseController {

    private final SearchYoutubeVideosService searchYoutubeVideosService;

    @GetMapping("/video")
    public ResponseEntity<BaseResponse<YoutubeVideoListResponseDto>> searchVideos(
        @PrincipalId long userId,
        @RequestParam String query,
        @RequestParam(required = false) String pageToken) {
        YoutubeVideoListResponseDto responseDto = searchYoutubeVideosService.searchYoutubeVideos(query, pageToken);
        return createBaseResponse(HttpStatus.OK, "유튜브 영상 조회 완료", responseDto);
    }
}
