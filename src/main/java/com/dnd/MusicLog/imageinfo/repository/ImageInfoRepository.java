package com.dnd.MusicLog.imageinfo.repository;

import com.dnd.MusicLog.imageinfo.entity.ImageInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageInfoRepository extends JpaRepository<ImageInfo, Long> {

    List<ImageInfo> findAllByLogId(long logId);

}
