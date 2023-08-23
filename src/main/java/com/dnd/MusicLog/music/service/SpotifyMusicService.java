package com.dnd.MusicLog.music.service;

import com.dnd.MusicLog.music.dto.SpotifyAlbumResponse;
import com.dnd.MusicLog.music.dto.SpotifyArtistResponse;
import com.dnd.MusicLog.music.dto.SpotifyImageResponse;
import com.dnd.MusicLog.music.dto.SpotifyItemResponse;
import com.dnd.MusicLog.music.dto.SpotifyTokenResponseDto;
import com.dnd.MusicLog.music.dto.SpotifyTrackResponseDto;
import com.dnd.MusicLog.music.entity.spotify.SpotifyAlbum;
import com.dnd.MusicLog.music.entity.spotify.SpotifyArtist;
import com.dnd.MusicLog.music.entity.spotify.SpotifyMusic;
import com.dnd.MusicLog.music.entity.spotify.SpotifyMusicArtistRelation;
import com.dnd.MusicLog.music.repository.spotify.SpotifyAlbumRepository;
import com.dnd.MusicLog.music.repository.spotify.SpotifyArtistRepository;
import com.dnd.MusicLog.music.repository.spotify.SpotifyMusicArtistRelationRepository;
import com.dnd.MusicLog.music.repository.spotify.SpotifyMusicRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Service
public class SpotifyMusicService {

    private static final String TOKEN_REQUEST_URL = "https://accounts.spotify.com/api/token";
    private static final String SEARCH_REQUEST_URL = "https://api.spotify.com/v1/search";
    private static final String SEARCH_TRACK_REQUEST_URL = "https://api.spotify.com/v1/tracks/";

    private final WebClient webClient;

    private final SpotifyMusicRepository musicRepository;
    private final SpotifyAlbumRepository albumRepository;
    private final SpotifyArtistRepository artistRepository;
    private final SpotifyMusicArtistRelationRepository relationRepository;

    @Value("${spotify.client.id}")
    private String SPOTIFY_CLIENT_ID;
    @Value("${spotify.client.secret}")
    private String SPOTIFY_CLIENT_SECRET;

    public SpotifyTrackResponseDto searchSpotifyTrackList(String query, int offset) {
        SpotifyTokenResponseDto spotifyTokenResponseDto = getToken();
        String accessToken = spotifyTokenResponseDto.accessToken();

        String queryString = createSearchTrackListQueryString(query, offset);

        return webClient.get()
            .uri(SEARCH_REQUEST_URL + queryString)
            .header("Authorization", "Bearer " + accessToken)
            .retrieve()
            .bodyToMono(SpotifyTrackResponseDto.class)
            .block();
    }

    @Transactional
    public SpotifyItemResponse searchSpotifyTrack(String spotifyId) {
        Optional<SpotifyMusic> spotifyMusicOptional = musicRepository.findBySpotifyId(spotifyId);

        if (spotifyMusicOptional.isPresent()) {
            return searchSpotifyTrackInRepository(spotifyMusicOptional.get());
        }

        SpotifyItemResponse itemResponse = searchSpotifyTrackByAPI(spotifyId);
        List<SpotifyArtistResponse> artistsResponse = itemResponse.artists();
        SpotifyAlbumResponse albumResponse = itemResponse.album();

        SpotifyMusic music = SpotifyMusic.builder()
            .spotifyId(itemResponse.id())
            .name(itemResponse.name())
            .build();

        musicRepository.save(music);

        SpotifyAlbum album = getOrCreateSpotifyAlbum(albumResponse);

        List<SpotifyArtist> artists = artistsResponse.stream()
            .map(this::getOrCreateSpotifyArtist)
            .toList();

        music.intoAlbum(album);
        artists.forEach(artist -> {
            SpotifyMusicArtistRelation relation = SpotifyMusicArtistRelation.builder()
                .artist(artist)
                .music(music)
                .build();
            relationRepository.save(relation);
        });

        return searchSpotifyTrackInRepository(music);
    }

    private SpotifyItemResponse searchSpotifyTrackInRepository(SpotifyMusic spotifyMusic) {
        SpotifyAlbum album = spotifyMusic.getAlbum();
        List<SpotifyImageResponse> albumImageResponse = imageUrlToResponse(album.getImageUrl());
        SpotifyAlbumResponse spotifyAlbumResponse =
            new SpotifyAlbumResponse(
                album.getSpotifyId(),
                album.getName(),
                album.getReleaseDate(),
                albumImageResponse);

        List<SpotifyMusicArtistRelation> relations = relationRepository.findAllByMusic(spotifyMusic);
        List<SpotifyArtistResponse> spotifyArtistResponses = relations.stream()
            .map(relation -> {
                SpotifyArtist artist = relation.getArtist();
                List<SpotifyImageResponse> artistImageResponse = imageUrlToResponse(artist.getImageUrl());
                return new SpotifyArtistResponse(artist.getSpotifyId(), artist.getName(), artistImageResponse);
            }).toList();

        return new SpotifyItemResponse(
            spotifyMusic.getSpotifyId(),
            spotifyMusic.getName(),
            spotifyAlbumResponse,
            spotifyArtistResponses);
    }

    private SpotifyAlbum getOrCreateSpotifyAlbum(SpotifyAlbumResponse spotifyAlbumResponse) {
        Optional<SpotifyAlbum> spotifyAlbumOptional =
            albumRepository.findBySpotifyId(spotifyAlbumResponse.id());

        if (spotifyAlbumOptional.isPresent()) {
            return spotifyAlbumOptional.get();
        }

        SpotifyImageResponse image = Optional.ofNullable(spotifyAlbumResponse.images())
            .orElse(List.of())
            .stream()
            .max(Comparator.comparingInt(SpotifyImageResponse::width))
            .orElse(null);

        SpotifyAlbum album = SpotifyAlbum.builder()
            .spotifyId(spotifyAlbumResponse.id())
            .name(spotifyAlbumResponse.name())
            .imageUrl(image != null ? image.url() : null)
            .releaseDate(spotifyAlbumResponse.releaseDate())
            .build();

        return albumRepository.save(album);
    }

    private SpotifyArtist getOrCreateSpotifyArtist(SpotifyArtistResponse spotifyArtistResponse) {
        Optional<SpotifyArtist> spotifyArtistOptional = artistRepository.findBySpotifyId(spotifyArtistResponse.id());

        if (spotifyArtistOptional.isPresent()) {
            return spotifyArtistOptional.get();
        }

        SpotifyImageResponse image = Optional.ofNullable(spotifyArtistResponse.images())
            .orElse(List.of())
            .stream()
            .max(Comparator.comparingInt(SpotifyImageResponse::width))
            .orElse(null);

        SpotifyArtist artist = SpotifyArtist.builder()
            .spotifyId(spotifyArtistResponse.id())
            .name(spotifyArtistResponse.name())
            .imageUrl(image != null ? image.url() : null)
            .build();

        return artistRepository.save(artist);
    }


    private List<SpotifyImageResponse> imageUrlToResponse(String imageUrl) {
        if (imageUrl == null) {
            return List.of();
        }
        return List.of(new SpotifyImageResponse(imageUrl, 0, 0));
    }

    private SpotifyItemResponse searchSpotifyTrackByAPI(String spotifyId) {
        SpotifyTokenResponseDto spotifyTokenResponseDto = getToken();
        String accessToken = spotifyTokenResponseDto.accessToken();

        String queryString = createSearchTrackQueryString(spotifyId);

        return webClient.get()
            .uri(SEARCH_TRACK_REQUEST_URL + queryString)
            .header("Authorization", "Bearer " + accessToken)
            .retrieve()
            .bodyToMono(SpotifyItemResponse.class)
            .block();
    }

    // type, market, limit 은 정책에 따라 수정
    private String createSearchTrackListQueryString(String query, int offset) {
        return "?q="
            + query
            + "&offset="
            + offset
            + "&type=track&market=KR&limit=10";
    }

    private String createSearchTrackQueryString(String spotifyId) {
        return spotifyId
            + "?market=KR";
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
