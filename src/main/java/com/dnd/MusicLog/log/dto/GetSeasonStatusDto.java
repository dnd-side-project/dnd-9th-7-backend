package com.dnd.MusicLog.log.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetSeasonStatusDto {
    private boolean SPRING = false;
    private boolean SUMMER = false;
    private boolean AUTUMN = false;
    private boolean WINTER = false;
}
