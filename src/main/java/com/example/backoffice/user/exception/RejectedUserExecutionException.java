package com.example.backoffice.user.exception;


import com.example.backoffice.global.exception.ErrorCode;
import com.example.backoffice.global.exception.RestApiException;

public class RejectedUserExecutionException extends RestApiException {

    public RejectedUserExecutionException(ErrorCode errorCode) {
        super(errorCode);
    }
}
