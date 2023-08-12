package com.dnd.MusicLog.log.controller;

import com.dnd.MusicLog.global.common.BaseController;
import com.dnd.MusicLog.global.common.BaseResponse;
import com.dnd.MusicLog.log.dto.YoutubeVideoListResponseDto;
import com.dnd.MusicLog.log.service.SearchYoutubeVideosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class LogController extends BaseController {

    private final SearchYoutubeVideosService searchYoutubeVideosService;

    @GetMapping("/api/youtube")
    public ResponseEntity<BaseResponse<YoutubeVideoListResponseDto>> searchVideos(@RequestParam String query,
                                                                                  @RequestParam(required = false) String pageToken) {

        YoutubeVideoListResponseDto responseDto = searchYoutubeVideosService.searchYoutubeVideos(query, pageToken);
        return createResponseEntity(HttpStatus.OK, "유튜브 영상 조회 완료", responseDto);

    }
}
