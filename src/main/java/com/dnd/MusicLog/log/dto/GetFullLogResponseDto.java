package com.dnd.MusicLog.log.dto;

import java.time.LocalDate;
import java.util.List;

public record GetFullLogResponseDto(String albumImageUrl, List<String> artists, String name, LocalDate date,
                                    String feeling, String time, String weather, String location, String review,
                                    String record, List<String> fileUrls, String title, String channelTitle,
                                    String publishedAt, String youtubeId) {
}
