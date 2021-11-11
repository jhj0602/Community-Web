package com.ssafy.community.post.exception;

public class NoPostFoundException extends IllegalArgumentException {
    public NoPostFoundException() {
        super("존재하지 않는 게시물입니다.");
    }
}