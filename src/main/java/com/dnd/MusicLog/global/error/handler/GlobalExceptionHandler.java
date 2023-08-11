package com.dnd.MusicLog.global.error.handler;

import com.dnd.MusicLog.global.error.dto.ErrorResponse;
import com.dnd.MusicLog.global.error.exception.BusinessLogicException;
import com.dnd.MusicLog.global.error.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Valid & Validated annotation 과정에서의 오류 핸들링.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException handle!!", e);
        ErrorResponse errorResponse = buildErrorResponse(ErrorCode.BAD_REQUEST);
        HttpStatus httpStatus = ErrorCode.BAD_REQUEST.getStatus();
        return buildResponseEntity(errorResponse, httpStatus);
    }

    // 서비스 로직 내에서의 Exception 핸들링.
    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<ErrorResponse> BusinessLoginExceptionHandler(BusinessLogicException e) {
        log.error("BusinessLoginException handle!!", e);
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse errorResponse = buildErrorResponse(errorCode);
        HttpStatus httpStatus = errorCode.getStatus();
        return buildResponseEntity(errorResponse, httpStatus);
    }

    // 그 밖의 모든 Exception 핸들링.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> MethodArgumentNotValidExceptionHandler(Exception e) {
        log.error("Exception handle!!", e);
        ErrorResponse errorResponse = buildErrorResponse(ErrorCode.SERVER_ERROR);
        HttpStatus httpStatus = ErrorCode.SERVER_ERROR.getStatus();
        return buildResponseEntity(errorResponse, httpStatus);
    }

    private ErrorResponse buildErrorResponse(ErrorCode errorCode) {
        return ErrorResponse.builder()
            .status(errorCode.getStatus().value())
            .message(errorCode.getMessage())
            .build();
    }

    private ResponseEntity<ErrorResponse> buildResponseEntity(ErrorResponse errorResponse, HttpStatus httpStatus) {
        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}
