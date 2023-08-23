package com.dnd.MusicLog.imageinfo.repository;

import com.dnd.MusicLog.imageinfo.entity.ImageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ImageInfoRepository extends JpaRepository<ImageInfo, Long> {

    @Query("SELECT i.imageName FROM ImageInfo i WHERE i.log.id = :logId ORDER BY i.createdDate ASC")
    List<String> findAllByLogIdOrderByCreatedDateAsc(@Param("logId") long logId);

    Optional<ImageInfo> findByLogId(long logId);

    Optional<ImageInfo> findByImageName(String imageName);

    void deleteByImageName(String imageName);
}
