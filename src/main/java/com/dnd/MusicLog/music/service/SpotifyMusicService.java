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

        // TODO: 반환하기 전 repository 에 저장하는 로직 구성 필요
        return searchSpotifyTrackByAPI(spotifyId);
    }

    private SpotifyItemResponse searchSpotifyTrackInRepository(SpotifyMusic spotifyMusic) {
        SpotifyAlbum album = spotifyMusic.getAlbum();
        SpotifyImageResponse albumImage = new SpotifyImageResponse(album.getImageUrl(), 0, 0);
        SpotifyAlbumResponse spotifyAlbumResponse =
            new SpotifyAlbumResponse(
                album.getSpotifyId(),
                album.getName(),
                album.getReleaseDate(),
                List.of(albumImage));

        List<SpotifyMusicArtistRelation> relations = relationRepository.findAllByMusic(spotifyMusic);
        List<SpotifyArtistResponse> spotifyArtistResponses = relations.stream().map(relation -> {
            SpotifyArtist artist = relation.getArtist();
            SpotifyImageResponse image = new SpotifyImageResponse(artist.getImageUrl(), 0, 0);
            return new SpotifyArtistResponse(artist.getSpotifyId(), artist.getName(), List.of(image));
        }).toList();

        return new SpotifyItemResponse(
            spotifyMusic.getSpotifyId(),
            spotifyMusic.getName(),
            spotifyAlbumResponse,
            spotifyArtistResponses);
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
