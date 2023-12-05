package com.example.backoffice.domain.post.exception;

import com.example.backoffice.global.exception.ErrorCode;
import com.example.backoffice.global.exception.RestApiException;

public class PostExistException extends RestApiException {

    public PostExistException(ErrorCode errorCode) {
        super(errorCode);
    }
}
