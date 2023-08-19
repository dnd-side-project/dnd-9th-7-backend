package com.dnd.MusicLog.music.service;

import com.dnd.MusicLog.global.error.exception.BusinessLogicException;
import com.dnd.MusicLog.global.error.exception.ErrorCode;
import com.dnd.MusicLog.music.dto.CustomMusicItem;
import com.dnd.MusicLog.music.dto.SaveCustomMusicRequestDto;
import com.dnd.MusicLog.music.dto.SaveCustomMusicResponseDto;
import com.dnd.MusicLog.music.dto.SearchCustomMusicResponseDto;
import com.dnd.MusicLog.music.dto.SpotifyTokenResponseDto;
import com.dnd.MusicLog.music.dto.SpotifyTrackResponseDto;
import com.dnd.MusicLog.music.entity.custom.CustomMusic;
import com.dnd.MusicLog.music.repository.custom.CustomMusicRepository;
import com.dnd.MusicLog.user.entity.User;
import com.dnd.MusicLog.user.service.OAuthLoginService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Service
public class MusicService {

    private static final String TOKEN_REQUEST_URL = "https://accounts.spotify.com/api/token";
    private static final String SEARCH_REQUEST_URL = "https://api.spotify.com/v1/search";

    private final OAuthLoginService oAuthLoginService;

    private final WebClient webClient;
    private final CustomMusicRepository customMusicRepository;

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

    @Transactional(readOnly = true)
    public SearchCustomMusicResponseDto searchCustomMusic(long userId, String query, int offset, int size) {
        PageRequest pageRequest = PageRequest.of(offset, size);

        List<CustomMusic> customMusic =
            customMusicRepository.searchAllByUserIdAndQuery(userId, query, pageRequest);

        List<CustomMusicItem> items = customMusic.stream()
            .map(CustomMusicItem::new)
            .collect(Collectors.toList());

        return new SearchCustomMusicResponseDto(offset, customMusic.size(), items);
    }

    @Transactional(readOnly = true)
    public CustomMusicItem searchCustomMusic(long userId, long customMusicId) {
        User user = oAuthLoginService.getUser(userId);
        
        CustomMusic music = customMusicRepository.findByIdAndAuthor(customMusicId, user)
            .orElseThrow(() -> new BusinessLogicException(ErrorCode.NOT_FOUND));

        return new CustomMusicItem(music);
    }


    @Transactional
    public SaveCustomMusicResponseDto saveCustomMusic(long userId, SaveCustomMusicRequestDto requestDto) {
        User user = oAuthLoginService.getUser(userId);

        CustomMusic customMusic = CustomMusic.builder()
            .name(requestDto.name())
            .artist(requestDto.artist())
            .imageUrl(requestDto.imageUrl())
            .author(user)
            .build();

        customMusicRepository.save(customMusic);

        return new SaveCustomMusicResponseDto(customMusic);
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
