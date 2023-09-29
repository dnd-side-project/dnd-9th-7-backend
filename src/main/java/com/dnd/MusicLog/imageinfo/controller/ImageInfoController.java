package com.dnd.MusicLog.imageinfo.controller;

import com.dnd.MusicLog.global.common.BaseController;
import com.dnd.MusicLog.global.common.BaseResponse;
import com.dnd.MusicLog.global.common.SuccessResponse;
import com.dnd.MusicLog.global.jwt.util.JwtTokenProvider;
import com.dnd.MusicLog.imageinfo.service.ImageInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/api/log")
@RestController
public class ImageInfoController extends BaseController {

    private final JwtTokenProvider jwtTokenProvider;
    private final ImageInfoService imageInfoService;

    @PostMapping("/image")
    public ResponseEntity<BaseResponse<String>> uploadImage(@RequestHeader(name = "Authorization") String bearerToken,
        @RequestPart("images") MultipartFile multipartFile) {
        jwtTokenProvider.extractAccessTokenSubject(bearerToken);
        String imageName = imageInfoService.uploadImage(multipartFile);
        return createBaseResponse(HttpStatus.CREATED, "이미지 저장 완료", imageName);
    }

    @DeleteMapping("/image")
    public ResponseEntity<SuccessResponse> deleteImage(@RequestHeader(name = "Authorization") String bearerToken,
        @RequestParam String fileName) {
        jwtTokenProvider.extractAccessTokenSubject(bearerToken);
        imageInfoService.deleteImage(fileName);
        return createSuccessResponse(HttpStatus.OK, "이미지 삭제 완료");
    }

    @GetMapping("/image")
    public ResponseEntity<BaseResponse<String>> searchImages(@RequestHeader(name = "Authorization") String bearerToken) {
        jwtTokenProvider.extractAccessTokenSubject(bearerToken);
        String fileName = imageInfoService.searchImage(1);
        return createBaseResponse(HttpStatus.OK, "이미지 조회 완료", fileName);
    }
}
