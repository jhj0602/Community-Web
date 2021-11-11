package com.ssafy.community.post.controllerAdvice;

import com.ssafy.community.post.exception.NoPostFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.skhu.luxuryshop.post.controller")
@Slf4j
public class PostExceptionControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.badRequest().body(e.getBindingResult().getFieldError().getDefaultMessage());
    }
    @ExceptionHandler(NoPostFoundException.class)
    public ResponseEntity<String> noPostFoundException(NoPostFoundException e){
        log.error(e.getMessage(),e);
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
