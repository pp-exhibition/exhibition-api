package com.pp.response;


import com.pp.exception.CommonResponseCode;

public record ApiResponse<T>(
        boolean success,
        String code,
        String message, T data
) {
    public static <T> ApiResponse<T> success(CommonResponseCode responseCode) {
        return new ApiResponse<>(true, responseCode.getCode(), responseCode.getMessage(), null);
    }

    public static <T> ApiResponse<T> success(CommonResponseCode responseCode, T data) {
        return new ApiResponse<>(true, responseCode.getCode(), responseCode.getMessage(), data);
    }

    public static <T> ApiResponse<T> fail(CommonResponseCode responseCode) {
        return new ApiResponse<>(false, responseCode.getCode(), responseCode.getMessage(), null);
    }

    public static <T> ApiResponse<T> fail(CommonResponseCode responseCode, T data) {
        return new ApiResponse<>(false, responseCode.getCode(), responseCode.getMessage(), data);
    }

    public static <T> ApiResponse<String> error(CommonResponseCode responseCode, RuntimeException exception) {
        return new ApiResponse<>(false, responseCode.getCode(), responseCode.getMessage(), exception.getMessage());
    }

}
