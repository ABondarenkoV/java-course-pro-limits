package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.ResponseErrorDto;
import org.example.exception.InsufficientLimitException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseErrorDto handleOtherExceptions(Exception ex) {
        log.error("Уппссс, неожиданная ошибка...", ex);
        return new ResponseErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getMessage());
    }

    @ExceptionHandler(InsufficientLimitException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseErrorDto handleEntityNotFound(InsufficientLimitException ex) {
        log.warn("Превышен лимит: {}", ex.getMessage());
        return new ResponseErrorDto(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
    }

}
