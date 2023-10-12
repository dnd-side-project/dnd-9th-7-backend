package com.dnd.MusicLog.log.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetTimeStatusDto {

    private boolean MORNING = false;
    private boolean LUNCH = false;
    private boolean DINNER = false;
    private boolean DAWN = false;

}
