package com.dnd.MusicLog.youtubeinfo.repository;

import com.dnd.MusicLog.youtubeinfo.entity.YoutubeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface YoutubeInfoRepository extends JpaRepository<YoutubeInfo, Long> {

}
