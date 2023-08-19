package com.dnd.MusicLog.imageinfo.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.dnd.MusicLog.global.error.exception.BusinessLogicException;
import com.dnd.MusicLog.global.error.exception.ErrorCode;
import com.dnd.MusicLog.imageinfo.dto.FileNamesResponseDto;
import com.dnd.MusicLog.imageinfo.entity.ImageInfo;
import com.dnd.MusicLog.imageinfo.repository.ImageInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageInfoService {

    private static final String DIRECTORY_NAME = "images";

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;
    private final ImageInfoRepository imageInfoRepository;

    @Transactional
    public FileNamesResponseDto uploadImages(List<MultipartFile> multipartFile) {

        if (multipartFile.size() > 10) {
            throw new BusinessLogicException(ErrorCode.BAD_REQUEST_MULTIPART);
        }

        List<String> fileNameList = new ArrayList<>();
        List<ImageInfo> imageInfoList = new ArrayList<>();

        multipartFile.forEach(file -> {
            String fileName = createFileName(file.getOriginalFilename(), DIRECTORY_NAME);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try(InputStream inputStream = file.getInputStream()) {
                // S3에 이미지 업로드
                amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            } catch(IOException e) {
                throw new BusinessLogicException(ErrorCode.SERVER_ERROR);
            }

            fileNameList.add(fileName);

            // ImageInfo 객체 생성 후 리스트에 추가
            ImageInfo imageInfo = ImageInfo.builder()
                .logId(1) // TODO : 로그 ID 값으로 지정, 로그 테이블과 연관관계 설정 후 변경예정.
                .imageName(fileName)
                .build();

            imageInfoList.add(imageInfo);
        });

        imageInfoRepository.saveAll(imageInfoList);

        return FileNamesResponseDto.builder()
            .fileNames(fileNameList)
            .build();

    }

    @Transactional
    public void deleteImage(String fileName) {
        getFileExtension(fileName);
        ImageInfo imageInfo = imageInfoRepository.findByImageName(fileName)
            .orElseThrow(() -> new BusinessLogicException(ErrorCode.NOT_FOUND));

        imageInfoRepository.deleteByImageName(imageInfo.getImageName());
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }

    // TODO : 로그 테이블 생성 후 조인을 이용할 예정
    public FileNamesResponseDto searchImages(long logId) {
        List<ImageInfo> imageInfoList = imageInfoRepository.findAllByLogId(logId);

        if (imageInfoList.isEmpty()) {
            throw new BusinessLogicException(ErrorCode.NOT_FOUND);
        }

        List<String> fileNameList = new ArrayList<>();

        imageInfoList.forEach(imageInfo -> {
            fileNameList.add(imageInfo.getImageName());
        });

        return FileNamesResponseDto.builder()
            .fileNames(fileNameList)
            .build();
    }

    private String createFileName(String fileName, String dirName) {
        return dirName + "/" + UUID.randomUUID() + fileName;
    }

    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new BusinessLogicException(ErrorCode.BAD_REQUEST_FILENAME);
        }
    }

}
