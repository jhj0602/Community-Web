package com.ssafy.community.common.controllerAdvice;

import com.ssafy.community.post.exception.NoPostFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.ssafy.community")
@Slf4j
public class PostExceptionControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getBindingResult().getFieldError().getDefaultMessage()));
    }

    @ExceptionHandler(NoPostFoundException.class)
    public ResponseEntity<ErrorResponse> noPostFoundException(NoPostFoundException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }
}
