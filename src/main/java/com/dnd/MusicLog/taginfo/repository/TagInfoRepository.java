package com.dnd.MusicLog.taginfo.repository;

import com.dnd.MusicLog.taginfo.entity.TagInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagInfoRepository extends JpaRepository<TagInfo, Long> {

    Optional<TagInfo> findByLogId(long logId);

}
