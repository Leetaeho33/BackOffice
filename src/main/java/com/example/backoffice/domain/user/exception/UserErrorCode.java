package com.example.backoffice.domain.user.exception;

import com.example.backoffice.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    REJECTED_USER_EXECUTION(HttpStatus.FORBIDDEN, "수정 권한이 없어요!"),
    NON_USER_EXSIST(HttpStatus.NOT_FOUND, "해당 사용자가 존재하지 않아요!"),
    ALREADY_EXSIST_USER(HttpStatus.FORBIDDEN, "이미 존재하는 사용자에요!"),
    PASSWORD_IS_NOT_MATCH(HttpStatus.FORBIDDEN, "비밀번호가 일치하지 않아요!");
    private final HttpStatus httpStatus;
    private final String message;
}