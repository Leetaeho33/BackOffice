package com.example.backoffice.user.exception;

import com.example.backoffice.global.exception.ErrorCode;
import com.example.backoffice.global.exception.RestApiException;

public class PasswordIsNotMatchException extends RestApiException {

    public PasswordIsNotMatchException(ErrorCode errorCode) {
        super(errorCode);
    }
}
