package com.dnd.MusicLog.music.service;

import com.dnd.MusicLog.music.dto.SpotifyTokenResponseDto;
import com.dnd.MusicLog.music.dto.SpotifyTrackResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Service
public class SpotifyMusicService {

    private static final String TOKEN_REQUEST_URL = "https://accounts.spotify.com/api/token";
    private static final String SEARCH_REQUEST_URL = "https://api.spotify.com/v1/search";

    private final WebClient webClient;

    @Value("${spotify.client.id}")
    private String SPOTIFY_CLIENT_ID;
    @Value("${spotify.client.secret}")
    private String SPOTIFY_CLIENT_SECRET;

    public SpotifyTrackResponseDto searchSpotifyMusic(String query, int offset) {
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
