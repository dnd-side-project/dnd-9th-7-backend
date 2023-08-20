package com.dnd.MusicLog.music.entity.spotify;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSpotifyAlbum is a Querydsl query type for SpotifyAlbum
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSpotifyAlbum extends EntityPathBase<SpotifyAlbum> {

    private static final long serialVersionUID = 1622652349L;

    public static final QSpotifyAlbum spotifyAlbum = new QSpotifyAlbum("spotifyAlbum");

    public final com.dnd.MusicLog.global.common.QBaseTimeEntity _super = new com.dnd.MusicLog.global.common.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final StringPath releaseDate = createString("releaseDate");

    public final StringPath spotifyId = createString("spotifyId");

    public QSpotifyAlbum(String variable) {
        super(SpotifyAlbum.class, forVariable(variable));
    }

    public QSpotifyAlbum(Path<? extends SpotifyAlbum> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSpotifyAlbum(PathMetadata metadata) {
        super(SpotifyAlbum.class, metadata);
    }

}

