package com.example.backoffice.domain.user.exception;

import com.example.backoffice.global.exception.ErrorCode;
import com.example.backoffice.global.exception.RestApiException;

public class NonLoginUserException extends RestApiException {
    public NonLoginUserException(ErrorCode errorCode) {
        super(errorCode);
    }
}
