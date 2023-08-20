package com.dnd.MusicLog.music.entity.spotify;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSpotifyArtist is a Querydsl query type for SpotifyArtist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSpotifyArtist extends EntityPathBase<SpotifyArtist> {

    private static final long serialVersionUID = -1231318599L;

    public static final QSpotifyArtist spotifyArtist = new QSpotifyArtist("spotifyArtist");

    public final com.dnd.MusicLog.global.common.QBaseTimeEntity _super = new com.dnd.MusicLog.global.common.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final StringPath spotifyId = createString("spotifyId");

    public QSpotifyArtist(String variable) {
        super(SpotifyArtist.class, forVariable(variable));
    }

    public QSpotifyArtist(Path<? extends SpotifyArtist> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSpotifyArtist(PathMetadata metadata) {
        super(SpotifyArtist.class, metadata);
    }

}

