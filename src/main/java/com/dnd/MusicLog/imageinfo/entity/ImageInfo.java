package com.dnd.MusicLog.imageinfo.entity;

import com.dnd.MusicLog.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "image_info")
@Entity
public class ImageInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // TODO : 로그 엔티티 생성 후 연관관계 설정할 것.
    @Column(name = "log_id", nullable = false)
    private long logId;


    @Column(name = "image_name", nullable = false)
    private String imageName;

    @Builder
    public ImageInfo(long logId, String imageName) {
        this.logId = logId;
        this.imageName = imageName;
    }
}