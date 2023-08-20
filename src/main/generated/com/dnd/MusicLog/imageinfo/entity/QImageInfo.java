package com.dnd.MusicLog.imageinfo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QImageInfo is a Querydsl query type for ImageInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QImageInfo extends EntityPathBase<ImageInfo> {

    private static final long serialVersionUID = -1888292591L;

    public static final QImageInfo imageInfo = new QImageInfo("imageInfo");

    public final com.dnd.MusicLog.global.common.QBaseTimeEntity _super = new com.dnd.MusicLog.global.common.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageName = createString("imageName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final NumberPath<Long> logId = createNumber("logId", Long.class);

    public QImageInfo(String variable) {
        super(ImageInfo.class, forVariable(variable));
    }

    public QImageInfo(Path<? extends ImageInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QImageInfo(PathMetadata metadata) {
        super(ImageInfo.class, metadata);
    }

}

