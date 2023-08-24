package com.dnd.MusicLog.youtubeinfo.service;

import com.dnd.MusicLog.log.dto.YoutubeVideoListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Service
public class SearchYoutubeVideosService {

    private static final String SEARCH_REQUEST_URL = "https://www.googleapis.com/youtube/v3/search";

    private final WebClient webClient;

    @Value("${youtube.secret-key}")
    private String YOUTUBE_SECKET_KEY;

    public YoutubeVideoListResponseDto searchYoutubeVideos(String query, String pageToken) {

        String queryString = getQueryString(query, pageToken);

        YoutubeVideoListResponseDto response = webClient.get()
            .uri(SEARCH_REQUEST_URL + queryString)
            .retrieve()
            .bodyToMono(YoutubeVideoListResponseDto.class)
            .block();

        return response;
    }

    private String getQueryString(String query, String pageToken) {

        StringBuilder queryString = new StringBuilder("?part=snippet&type=video&maxResults=25&q=")
            .append(query)
            .append("&key=")
            .append(YOUTUBE_SECKET_KEY);

        if (pageToken != null) {
            queryString.append("&pageToken=").append(pageToken);
        }

        return queryString.toString();

    }
}
