package com.dnd.MusicLog.taginfo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTagInfo is a Querydsl query type for TagInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTagInfo extends EntityPathBase<TagInfo> {

    private static final long serialVersionUID = 1674756273L;

    public static final QTagInfo tagInfo = new QTagInfo("tagInfo");

    public final com.dnd.MusicLog.global.common.QBaseTimeEntity _super = new com.dnd.MusicLog.global.common.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final EnumPath<com.dnd.MusicLog.taginfo.enums.Feeling> feeling = createEnum("feeling", com.dnd.MusicLog.taginfo.enums.Feeling.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final NumberPath<Long> logId = createNumber("logId", Long.class);

    public final EnumPath<com.dnd.MusicLog.taginfo.enums.Season> season = createEnum("season", com.dnd.MusicLog.taginfo.enums.Season.class);

    public final EnumPath<com.dnd.MusicLog.taginfo.enums.Time> time = createEnum("time", com.dnd.MusicLog.taginfo.enums.Time.class);

    public final EnumPath<com.dnd.MusicLog.taginfo.enums.Weather> weather = createEnum("weather", com.dnd.MusicLog.taginfo.enums.Weather.class);

    public QTagInfo(String variable) {
        super(TagInfo.class, forVariable(variable));
    }

    public QTagInfo(Path<? extends TagInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTagInfo(PathMetadata metadata) {
        super(TagInfo.class, metadata);
    }

}

