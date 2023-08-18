package com.dnd.MusicLog.image.entity;

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

    @Column(name = "log_id", nullable = false)
    private long logId;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "image_name", nullable = false)
    private String imageName;

    @Builder
    public ImageInfo(String imageUrl, String imageName) {
        this.imageUrl = imageUrl;
        this.imageName = imageName;
    }
}
