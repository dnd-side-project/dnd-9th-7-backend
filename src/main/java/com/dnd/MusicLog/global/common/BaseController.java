package com.dnd.MusicLog.global.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {

    protected <T> ResponseEntity<BaseResponse> createResponseEntity(HttpStatus httpStatus, String message, T data) {
        return ResponseEntity
            .status(httpStatus)
            .body(BaseResponse.builder()
                .status(httpStatus.value())
                .message(message)
                .data(data)
                .build());
    }

}
