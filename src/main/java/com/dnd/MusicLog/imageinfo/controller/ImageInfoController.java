package com.dnd.MusicLog.imageinfo.controller;

import com.dnd.MusicLog.global.common.BaseController;
import com.dnd.MusicLog.global.common.BaseResponse;
import com.dnd.MusicLog.global.common.SuccessResponse;
import com.dnd.MusicLog.global.jwt.util.JwtTokenProvider;
import com.dnd.MusicLog.imageinfo.dto.FileNamesResponseDto;
import com.dnd.MusicLog.imageinfo.service.ImageInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/log")
@RestController
public class ImageInfoController extends BaseController {

    private final JwtTokenProvider jwtTokenProvider;
    private final ImageInfoService imageInfoService;

    @PostMapping("/image")
    public ResponseEntity<BaseResponse<FileNamesResponseDto>> uploadImages(@RequestHeader(name = "Authorization") String bearerToken,
                                                                           @RequestPart("images") List<MultipartFile> multipartFile) {
        jwtTokenProvider.extractAccessTokenSubject(bearerToken);
        FileNamesResponseDto responseDto = imageInfoService.uploadImages(multipartFile);
        return createBaseResponse(HttpStatus.CREATED, "이미지 저장 완료", responseDto);
    }

    @DeleteMapping("/image")
    public ResponseEntity<SuccessResponse> deleteImage(@RequestHeader(name = "Authorization") String bearerToken,
                                                       @RequestParam String fileName) {
        jwtTokenProvider.extractAccessTokenSubject(bearerToken);
        imageInfoService.deleteImage(fileName);
        return createSuccessResponse(HttpStatus.OK, "이미지 삭제 완료");
    }

    @GetMapping("/image")
    public ResponseEntity<BaseResponse<FileNamesResponseDto>> searchImages(@RequestHeader(name = "Authorization") String bearerToken) {
        jwtTokenProvider.extractAccessTokenSubject(bearerToken);
        FileNamesResponseDto responseDto = imageInfoService.searchImages(1);
        return createBaseResponse(HttpStatus.OK, "이미지 조회 완료", responseDto);
    }
}
