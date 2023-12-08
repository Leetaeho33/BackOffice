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
    PASSWORD_IS_NOT_MATCH(HttpStatus.FORBIDDEN, "비밀번호가 일치하지 않아요!"),
    RECNTLY_SET_PASSWORD(HttpStatus.FORBIDDEN,"최근 3번안에 사용한 비밀번호는 사용할 수 없어요!"),
    NON_LOGIN_USER(HttpStatus.NOT_FOUND,"로그인 된 유저가 아니에요!"),
    NON_USERPAGE(HttpStatus.NOT_FOUND, "존재하지 않는 유저페이지 입니다.");
    private final HttpStatus httpStatus;
    private final String message;
}