package com.dnd.MusicLog.music.entity.spotify;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSpotifyMusic is a Querydsl query type for SpotifyMusic
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSpotifyMusic extends EntityPathBase<SpotifyMusic> {

    private static final long serialVersionUID = 1634018675L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSpotifyMusic spotifyMusic = new QSpotifyMusic("spotifyMusic");

    public final com.dnd.MusicLog.global.common.QBaseTimeEntity _super = new com.dnd.MusicLog.global.common.QBaseTimeEntity(this);

    public final QSpotifyAlbum album;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final StringPath spotifyId = createString("spotifyId");

    public QSpotifyMusic(String variable) {
        this(SpotifyMusic.class, forVariable(variable), INITS);
    }

    public QSpotifyMusic(Path<? extends SpotifyMusic> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSpotifyMusic(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSpotifyMusic(PathMetadata metadata, PathInits inits) {
        this(SpotifyMusic.class, metadata, inits);
    }

    public QSpotifyMusic(Class<? extends SpotifyMusic> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.album = inits.isInitialized("album") ? new QSpotifyAlbum(forProperty("album")) : null;
    }

}

