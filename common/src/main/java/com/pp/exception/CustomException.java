package com.pp.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final ResponseCode responseCode;

    public CustomException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }

    public CustomException(String message, ResponseCode responseCode) {
        super(message);
        this.responseCode = responseCode;
    }

}
