package com.dnd.MusicLog.global.error.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BusinessLogicException extends RuntimeException{
    private final ErrorCode errorCode;
}
