package com.example.backoffice.domain.post.exception;

import com.example.backoffice.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PostErrorCode implements ErrorCode {
    NO_POST(HttpStatus.NOT_FOUND, "존재하지 않는 게시물 입니다."),
    NO_AUTHORITY(HttpStatus.FORBIDDEN, "해당 권한이 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
