package com.dnd.MusicLog.log.dto;

import com.dnd.MusicLog.imageinfo.dto.FileNamesResponseDto;

public record GetLogRecordResponseDto (String record, FileNamesResponseDto fileNames){
}
