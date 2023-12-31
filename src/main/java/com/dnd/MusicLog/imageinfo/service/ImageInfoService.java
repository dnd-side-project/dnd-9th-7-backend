package com.dnd.MusicLog.imageinfo.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.dnd.MusicLog.global.error.exception.BusinessLogicException;
import com.dnd.MusicLog.global.error.exception.ErrorCode;
import com.dnd.MusicLog.imageinfo.dto.FileNamesResponseDto;
import com.dnd.MusicLog.imageinfo.entity.ImageInfo;
import com.dnd.MusicLog.imageinfo.repository.ImageInfoRepository;
import com.dnd.MusicLog.log.entity.Log;
import com.dnd.MusicLog.log.repository.LogRepository;
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
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ImageInfoService {

    private static final String DIRECTORY_NAME = "images";

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    private final AmazonS3 amazonS3;
    private final LogRepository logRepository;
    private final ImageInfoRepository imageInfoRepository;

    public String uploadImage(MultipartFile multipartFile) {
        String fileName = createFileName(multipartFile.getOriginalFilename(), DIRECTORY_NAME);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        try {
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, multipartFile.getInputStream(), objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch(IOException e) {
            throw new BusinessLogicException(ErrorCode.SERVER_ERROR);
        }

        return fileName;
    }

    @Transactional
    public FileNamesResponseDto uploadImages(long logId, List<MultipartFile> multipartFile) {

        if (multipartFile.size() > 10) {
            throw new BusinessLogicException(ErrorCode.BAD_REQUEST_MULTIPART);
        }

        List<String> fileNameList = new ArrayList<>();
        List<ImageInfo> imageInfoList = new ArrayList<>();

        Log log = logRepository.findById(logId).orElseThrow(() -> {
            throw new BusinessLogicException(ErrorCode.NOT_FOUND);
        });

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

            String imageUrl = "https://s3." + region + ".amazonaws.com/" + bucket + "/" + fileName;

            // ImageInfo 객체 생성 후 리스트에 추가
            ImageInfo imageInfo = ImageInfo.builder()
                .log(log)
                .imageName(fileName)
                .imageUrl(imageUrl)
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

    @Transactional
    public void deleteImages(long logId) {

        List<String> fileNames = imageInfoRepository.findAllImageNameByLogIdOrderByCreatedDateAsc(logId);

        for (String fileName : fileNames) {
            ImageInfo imageInfo = imageInfoRepository.findByImageName(fileName)
                .orElseThrow(() -> new BusinessLogicException(ErrorCode.NOT_FOUND));

            imageInfoRepository.delete(imageInfo);
        }

        List<DeleteObjectsRequest.KeyVersion> keys = fileNames.stream()
            .map(s3Key -> new DeleteObjectsRequest.KeyVersion(s3Key))
            .collect(Collectors.toList());

        if (!keys.isEmpty()) {
            amazonS3.deleteObjects(new DeleteObjectsRequest(bucket).withKeys(keys));
        }
    }


    public String searchImage(long logId) {
        ImageInfo imageInfo = imageInfoRepository.findByLogId(logId).orElseThrow(() -> {
            throw new BusinessLogicException(ErrorCode.NOT_FOUND);
        });

        return imageInfo.getImageName();

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
