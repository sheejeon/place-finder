package com.example.share.exception;

import com.example.share.domain.RestErrorResponse;
import com.google.maps.errors.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseEntity<RestErrorResponse> handleException(Throwable ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "Internal Server Error";

        if (ex instanceof BadRequestException) {
            status = HttpStatus.BAD_REQUEST;
            message = ex.getMessage();
        } else if (ex instanceof NotFoundException) {
            status = HttpStatus.NOT_FOUND;
            message = ex.getMessage();
        }

        RestErrorResponse error = new RestErrorResponse(status.value(), message);
        return new ResponseEntity<>(error, status);
    }
}
