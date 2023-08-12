package com.dnd.MusicLog.log.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record YoutubeVideoListResponseDto(@JsonProperty("nextPageToken") String nextToken,
                                          @JsonProperty("prevPageToken") String prevToken,
                                          YoutubeItem[] items) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record YoutubeItem(YoutubeResultId id,
                              YoutubeResultSnippet snippet) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record YoutubeResultId(@JsonProperty("videoId") String videoId) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record YoutubeResultSnippet(@JsonProperty("publishedAt") String publishedAt,
                                       @JsonProperty("title") String title,
                                       @JsonProperty("channelTitle") String channelTitle) {
    }

}

