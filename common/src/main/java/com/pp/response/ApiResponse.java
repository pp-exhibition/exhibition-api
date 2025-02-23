package com.pp.response;


import com.pp.exception.ResponseCode;

public record ApiResponse<T>(
        boolean success,
        String code,
        String message, T data
) {
    public static <T> ApiResponse<T> success(ResponseCode responseCode) {
        return new ApiResponse<>(true, responseCode.getCode(), responseCode.getMessage(), null);
    }

    public static <T> ApiResponse<T> success(ResponseCode responseCode, T data) {
        return new ApiResponse<>(true, responseCode.getCode(), responseCode.getMessage(), data);
    }

    public static <T> ApiResponse<T> fail(ResponseCode responseCode) {
        return new ApiResponse<>(false, responseCode.getCode(), responseCode.getMessage(), null);
    }

    public static <T> ApiResponse<T> fail(ResponseCode responseCode, T data) {
        return new ApiResponse<>(false, responseCode.getCode(), responseCode.getMessage(), data);
    }

    public static ApiResponse<String> error(ResponseCode responseCode, RuntimeException exception) {
        return new ApiResponse<>(false, responseCode.getCode(), responseCode.getMessage(), exception.getMessage());
    }

}
