package com.dnd.MusicLog.imageinfo.entity;

import com.dnd.MusicLog.global.common.BaseTimeEntity;
import com.dnd.MusicLog.log.entity.Log;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "log_id", nullable = false)
    private Log log;

    @Column(name = "image_name", nullable = false)
    private String imageName;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Builder
    public ImageInfo(Log log, String imageName, String imageUrl) {
        this.log = log;
        this.imageName = imageName;
        this.imageUrl = imageUrl;
    }
}
