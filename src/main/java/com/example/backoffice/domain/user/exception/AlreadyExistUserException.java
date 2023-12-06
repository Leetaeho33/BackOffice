package com.example.backoffice.domain.user.exception;


import com.example.backoffice.global.exception.ErrorCode;
import com.example.backoffice.global.exception.RestApiException;

public class AlreadyExistUserException extends RestApiException {

    public AlreadyExistUserException(ErrorCode errorCode) {
        super(errorCode);
    }

}
