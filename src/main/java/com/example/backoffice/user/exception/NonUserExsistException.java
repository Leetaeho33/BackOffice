package com.example.backoffice.user.exception;


import com.example.backoffice.global.exception.ErrorCode;
import com.example.backoffice.global.exception.RestApiException;

public class NonUserExsistException extends RestApiException {

    public NonUserExsistException(ErrorCode errorCode) {
        super(errorCode);
    }
}
