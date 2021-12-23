package com.ssafy.community.common.controllerAdvice;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
    private int status;
    private String message;

    public ErrorResponse(int status, String message) {
        super();
        this.status = status;
        this.message = message;
    }
}