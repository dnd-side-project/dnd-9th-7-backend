package com.dnd.MusicLog.music.entity.custom;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomMusic is a Querydsl query type for CustomMusic
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomMusic extends EntityPathBase<CustomMusic> {

    private static final long serialVersionUID = -97385941L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomMusic customMusic = new QCustomMusic("customMusic");

    public final com.dnd.MusicLog.global.common.QBaseTimeEntity _super = new com.dnd.MusicLog.global.common.QBaseTimeEntity(this);

    public final StringPath artist = createString("artist");

    public final com.dnd.MusicLog.user.entity.QUser author;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public QCustomMusic(String variable) {
        this(CustomMusic.class, forVariable(variable), INITS);
    }

    public QCustomMusic(Path<? extends CustomMusic> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustomMusic(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustomMusic(PathMetadata metadata, PathInits inits) {
        this(CustomMusic.class, metadata, inits);
    }

    public QCustomMusic(Class<? extends CustomMusic> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.author = inits.isInitialized("author") ? new com.dnd.MusicLog.user.entity.QUser(forProperty("author")) : null;
    }

}

