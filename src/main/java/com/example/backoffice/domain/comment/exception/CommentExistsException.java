package com.example.backoffice.domain.comment.exception;


import com.example.backoffice.global.exception.ErrorCode;
import com.example.backoffice.global.exception.RestApiException;

public class CommentExistsException extends RestApiException {

    public CommentExistsException(ErrorCode errorCode) {
        super(errorCode);
    }
}
