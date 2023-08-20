package com.dnd.MusicLog.music.entity.spotify;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSpotifyMusicArtistRelation is a Querydsl query type for SpotifyMusicArtistRelation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSpotifyMusicArtistRelation extends EntityPathBase<SpotifyMusicArtistRelation> {

    private static final long serialVersionUID = -1923812682L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSpotifyMusicArtistRelation spotifyMusicArtistRelation = new QSpotifyMusicArtistRelation("spotifyMusicArtistRelation");

    public final com.dnd.MusicLog.global.common.QBaseTimeEntity _super = new com.dnd.MusicLog.global.common.QBaseTimeEntity(this);

    public final QSpotifyArtist artist;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final QSpotifyMusic music;

    public QSpotifyMusicArtistRelation(String variable) {
        this(SpotifyMusicArtistRelation.class, forVariable(variable), INITS);
    }

    public QSpotifyMusicArtistRelation(Path<? extends SpotifyMusicArtistRelation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSpotifyMusicArtistRelation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSpotifyMusicArtistRelation(PathMetadata metadata, PathInits inits) {
        this(SpotifyMusicArtistRelation.class, metadata, inits);
    }

    public QSpotifyMusicArtistRelation(Class<? extends SpotifyMusicArtistRelation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.artist = inits.isInitialized("artist") ? new QSpotifyArtist(forProperty("artist")) : null;
        this.music = inits.isInitialized("music") ? new QSpotifyMusic(forProperty("music"), inits.get("music")) : null;
    }

}

