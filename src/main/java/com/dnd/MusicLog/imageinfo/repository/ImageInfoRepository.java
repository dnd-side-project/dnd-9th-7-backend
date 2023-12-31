package com.dnd.MusicLog.imageinfo.repository;

import com.dnd.MusicLog.imageinfo.entity.ImageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageInfoRepository extends JpaRepository<ImageInfo, Long> {

    @Query("SELECT i.imageUrl FROM ImageInfo i WHERE i.log.id = :logId ORDER BY i.createdDate ASC")
    List<String> findAllImageUrlByLogIdOrderByCreatedDateAsc(@Param("logId") long logId);

    @Query("SELECT i.imageName FROM ImageInfo i WHERE i.log.id = :logId ORDER BY i.createdDate ASC")
    List<String> findAllImageNameByLogIdOrderByCreatedDateAsc(@Param("logId") long logId);

    Optional<ImageInfo> findByLogId(long logId);

    Optional<ImageInfo> findByImageName(String imageName);

    void deleteByImageName(String imageName);
}
