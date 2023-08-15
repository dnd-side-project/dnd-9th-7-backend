package com.dnd.MusicLog.global.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {

    protected <T> ResponseEntity<BaseResponse<T>> createBaseResponse(HttpStatus httpStatus, String message, T data) {
        return ResponseEntity
            .status(httpStatus)
            .body(BaseResponse.<T>builder()
                .status(httpStatus.value())
                .message(message)
                .data(data)
                .build());
    }

    protected <T> ResponseEntity<SuccessResponse> createSuccessResponse(HttpStatus httpStatus, String message) {
        return ResponseEntity
            .status(httpStatus)
            .body(SuccessResponse.builder()
                .status(httpStatus.value())
                .message(message)
                .build());
    }

}
