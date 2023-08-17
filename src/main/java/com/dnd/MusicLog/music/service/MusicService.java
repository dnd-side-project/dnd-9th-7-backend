package com.dnd.MusicLog.music.service;

import com.dnd.MusicLog.global.error.exception.BusinessLogicException;
import com.dnd.MusicLog.global.error.exception.ErrorCode;
import com.dnd.MusicLog.music.dto.SaveMusicRequestDto;
import com.dnd.MusicLog.music.dto.SaveMusicRequestDto.AlbumRequestDto;
import com.dnd.MusicLog.music.dto.SaveMusicRequestDto.ArtistRequestDto;
import com.dnd.MusicLog.music.dto.SaveMusicRequestDto.MusicRequestDto;
import com.dnd.MusicLog.music.dto.SaveMusicResponseDto;
import com.dnd.MusicLog.music.dto.SpotifyTokenResponseDto;
import com.dnd.MusicLog.music.dto.SpotifyTrackResponseDto;
import com.dnd.MusicLog.music.entity.Album;
import com.dnd.MusicLog.music.entity.Artist;
import com.dnd.MusicLog.music.entity.Music;
import com.dnd.MusicLog.music.entity.MusicArtistRelation;
import com.dnd.MusicLog.music.repository.AlbumRepository;
import com.dnd.MusicLog.music.repository.ArtistRepository;
import com.dnd.MusicLog.music.repository.MusicArtistRelationRepository;
import com.dnd.MusicLog.music.repository.MusicRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Service
public class MusicService {

    private static final String TOKEN_REQUEST_URL = "https://accounts.spotify.com/api/token";
    private static final String SEARCH_REQUEST_URL = "https://api.spotify.com/v1/search";

    private final WebClient webClient;
    private final MusicRepository musicRepository;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;
    private final MusicArtistRelationRepository musicArtistRelationRepository;

    @Value("${spotify.client.id}")
    private String SPOTIFY_CLIENT_ID;
    @Value("${spotify.client.secret}")
    private String SPOTIFY_CLIENT_SECRET;

    public SpotifyTrackResponseDto searchMusic(String query, int offset) {
        SpotifyTokenResponseDto spotifyTokenResponseDto = getToken();
        String accessToken = spotifyTokenResponseDto.accessToken();

        String queryString = getQueryString(query, offset);

        return webClient.get()
            .uri(SEARCH_REQUEST_URL + queryString)
            .header("Authorization", "Bearer " + accessToken)
            .retrieve()
            .bodyToMono(SpotifyTrackResponseDto.class)
            .block();
    }

    @Transactional
    public SaveMusicResponseDto saveMusic(long userId, SaveMusicRequestDto saveMusicRequestDto) {
        MusicRequestDto musicRequestDto = saveMusicRequestDto.music();

        if (musicRequestDto.uniqueId() == null) {
            throw new BusinessLogicException(ErrorCode.BAD_REQUEST);
        }

        Optional<Music> musicOptional = findMusic(userId, musicRequestDto);

        if (musicOptional.isPresent()) {
            // music 기준으로 이미 정의된 음악의 경우 새로운 데이터를 생성하지 않고 기존 데이터를 반환
            Music music = musicOptional.get();
            List<MusicArtistRelation> relations = musicArtistRelationRepository.findAllByMusic(music);
            List<Artist> artists = relations.stream().map(MusicArtistRelation::getArtist).toList();

            return SaveMusicResponseDto.builder()
                .music(music)
                .artists(artists)
                .album(music.getAlbum())
                .build();
        }

        // music 기준으로 새로운 음악의 경우 artist, album 에 대해서 getOrCreate 진행 후 관계 매핑을 진행
        Music music = Music.builder()
            .name(musicRequestDto.name())
            .imageUrl(musicRequestDto.imageUrl())
            .uniqueId(musicRequestDto.uniqueId())
            .custom(musicRequestDto.custom())
            .releaseDate(musicRequestDto.releaseDate())
            .author(musicRequestDto.custom() ? userId : null)
            .build();

        musicRepository.save(music);

        List<ArtistRequestDto> artistRequestDto = validateArtistRequestDto(saveMusicRequestDto.artists());
        AlbumRequestDto albumRequestDto = validateAlbumRequestDto(saveMusicRequestDto.album());

        List<Artist> artists = artistRequestDto.stream()
            .map(dto -> getOrCreateArtist(userId, dto))
            .filter(Objects::nonNull)
            .toList();
        Album album = getOrCreateAlbum(userId, albumRequestDto);

        music.intoAlbum(album);
        artists.forEach(artist -> {
            MusicArtistRelation relation = MusicArtistRelation.builder()
                .music(music)
                .artist(artist)
                .build();
            musicArtistRelationRepository.save(relation);
        });

        return SaveMusicResponseDto.builder()
            .music(music)
            .artists(artists)
            .album(album)
            .build();
    }

    private AlbumRequestDto validateAlbumRequestDto(AlbumRequestDto albumRequestDto) {
        if (albumRequestDto == null) {
            return new AlbumRequestDto(null, null, null, false, null);
        }
        return albumRequestDto;
    }

    private List<ArtistRequestDto> validateArtistRequestDto(List<ArtistRequestDto> artistRequestDtos) {
        return artistRequestDtos == null ? new ArrayList<>() : artistRequestDtos;
    }

    private Optional<Music> findMusic(long userId, MusicRequestDto musicRequestDto) {
        if (musicRequestDto.custom()) {
            return musicRepository.findByAuthorAndUniqueId(userId, musicRequestDto.uniqueId());
        }
        return musicRepository.findByUniqueId(musicRequestDto.uniqueId());
    }

    private Artist getOrCreateArtist(long userId, ArtistRequestDto artistRequestDto) {
        if (!StringUtils.hasText(artistRequestDto.uniqueId())) {
            return null;
        }

        Optional<Artist> artistOptional = Optional.empty();

        if (artistRequestDto.custom()) {
            artistOptional = artistRepository.findByAuthorAndUniqueId(userId, artistRequestDto.uniqueId());
        }
        if (!artistRequestDto.custom()) {
            artistOptional = artistRepository.findByUniqueId(artistRequestDto.uniqueId());
        }

        if (artistOptional.isPresent()) {
            return artistOptional.get();
        }

        Artist artist = Artist.builder()
            .name(artistRequestDto.name())
            .imageUrl(artistRequestDto.imageUrl())
            .uniqueId(artistRequestDto.uniqueId())
            .custom(artistRequestDto.custom())
            .author(artistRequestDto.custom() ? userId : null)
            .build();

        return artistRepository.save(artist);
    }

    private Album getOrCreateAlbum(long userId, AlbumRequestDto albumRequestDto) {
        if (!StringUtils.hasText(albumRequestDto.uniqueId())) {
            return null;
        }

        Optional<Album> albumOptional = Optional.empty();

        if (albumRequestDto.custom()) {
            albumOptional = albumRepository.findByAuthorAndUniqueId(userId, albumRequestDto.uniqueId());
        }
        if (!albumRequestDto.custom()) {
            albumOptional = albumRepository.findByUniqueId(albumRequestDto.uniqueId());
        }

        if (albumOptional.isPresent()) {
            return albumOptional.get();
        }

        Album album = Album.builder()
            .name(albumRequestDto.name())
            .imageUrl(albumRequestDto.imageUrl())
            .uniqueId(albumRequestDto.uniqueId())
            .custom(albumRequestDto.custom())
            .releaseDate(albumRequestDto.releaseDate())
            .author(albumRequestDto.custom() ? userId : null)
            .build();

        return albumRepository.save(album);
    }

    // type, market, limit 은 정책에 따라 수정
    private String getQueryString(String query, int offset) {
        return "?q="
            + query
            + "&offset="
            + offset
            + "&type=track&market=KR&limit=10";
    }

    private SpotifyTokenResponseDto getToken() {
        String body =
            "grant_type=client_credentials&client_id=" + SPOTIFY_CLIENT_ID + "&client_secret=" + SPOTIFY_CLIENT_SECRET;

        WebClient webClient = WebClient.builder()
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .build();

        return webClient.post()
            .uri(TOKEN_REQUEST_URL)
            .body(BodyInserters.fromValue(body))
            .retrieve()
            .bodyToMono(SpotifyTokenResponseDto.class)
            .block();
    }
}
