package com.example.backoffice.domain.user.exception;

import com.example.backoffice.global.exception.ErrorCode;
import com.example.backoffice.global.exception.RestApiException;

public class RecentlySetPasswordException extends RestApiException {

    public RecentlySetPasswordException(ErrorCode errorCode) {
        super(errorCode);
    }
}
