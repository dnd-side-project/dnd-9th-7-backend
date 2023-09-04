package com.dnd.MusicLog.log.dto;

import java.util.List;

public record GetLogRecordResponseDto (String record, List<String> fileUrls){
}
