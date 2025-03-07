package com.pp.response;


import com.pp.exception.ResponseCode;

public record CommonResponse<T>(
        boolean success,
        String code,
        String message, T data
) {
    public static <T> CommonResponse<T> success(ResponseCode responseCode) {
        return new CommonResponse<>(true, responseCode.getCode(), responseCode.getMessage(), null);
    }

    public static <T> CommonResponse<T> success(ResponseCode responseCode, T data) {
        return new CommonResponse<>(true, responseCode.getCode(), responseCode.getMessage(), data);
    }

    public static <T> CommonResponse<T> fail(ResponseCode responseCode) {
        return new CommonResponse<>(false, responseCode.getCode(), responseCode.getMessage(), null);
    }

    public static <T> CommonResponse<T> fail(ResponseCode responseCode, T data) {
        return new CommonResponse<>(false, responseCode.getCode(), responseCode.getMessage(), data);
    }

    public static CommonResponse<String> error(ResponseCode responseCode, String message) {
        return new CommonResponse<>(false, responseCode.getCode(), message, null);
    }

    public static CommonResponse<String> error(ResponseCode responseCode, RuntimeException exception) {
        return new CommonResponse<>(false, responseCode.getCode(), responseCode.getMessage(), exception.getMessage());
    }

}
