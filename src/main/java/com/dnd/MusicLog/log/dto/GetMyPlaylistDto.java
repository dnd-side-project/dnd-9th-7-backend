package com.dnd.MusicLog.log.dto;

import java.util.List;

public record GetMyPlaylistDto(String feelingColor, String timeColor, String weatherColor, String seasonColor,
                               String date, String text, List<RandomMyPlaylistDto> randomMyPlaylistDtoList) {
}
