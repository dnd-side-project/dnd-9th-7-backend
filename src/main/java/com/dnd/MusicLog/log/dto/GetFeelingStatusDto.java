package com.dnd.MusicLog.log.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetFeelingStatusDto {

    private boolean HAPPINESS = false;
    private boolean EXCITEMENT = false;
    private boolean FLUTTER = false;
    private boolean SERENITY = false;
    private boolean EMPTINESS = false;
    private boolean DEPRESSION = false;
    private boolean SADNESS = false;
    private boolean ANGER = false;

}
