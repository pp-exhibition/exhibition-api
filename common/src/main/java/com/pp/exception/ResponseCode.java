package com.pp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    /**
     * Success (0200 ~ 0300)
     */
    SUCCESS("0200", "성공했습니다."),

    /**
     * Common (0600 ~ 0800)
     */
    INTERNAL_SERVER_ERROR("0600", "내부 서버 오류가 발생하였습니다."),
    IS_NULL_PARAM("0601", "파라미터가 올바르지 않습니다.(NULL)"),
    INVALID_PARAM("0602", "파라미터가 올바르지 않습니다."),
    INVALID_AUTHENTICATION("0603", "인증이 올바르지 않습니다."),
    NO_SUCH_METHOD("0604", "메소드를 찾을 수 없습니다."),
    NOT_FOUND_ENUM_CONSTANT("0605", "열거형 상수값을 찾을 수 없습니다."),
    S3_UPLOADER_ERROR("0606", "S3 업로드 중 오류가 발생하였습니다."),
    METHOD_ARG_NOT_VALID("0607", "유효성 오류가 발생했습니다."),
    INVALID_FILE_NAME_OR_EXTENSIONS("0608", "파일 이름 또는 확장자가 잘못되었습니다."),
    INVALID_LIST("0609", "리스트가 비어있거나 NULL 입니다."),

    FORBIDDEN("0700", "접근 권한이 없습니다."),
    UNAUTHORIZED("0701", "유효한 인증 자격이 없습니다."),

    /**
     * Member (1000 ~ 1100)
     */
    NOT_FOUND_MEMBER("1000", "회원 정보를 찾을 수 없습니다."),
    ALREADY_IN_USE_EMAIL("1001", "이미 사용중인 이메일입니다"),
    ALREADY_IN_USE_NICKNAME("1002", "이미 사용중인 닉네임입니다"),
    NOT_VALID_PARAM_EMAIL("1003", "입력한 이메일이 올바르지 않습니다."),
    NOT_VALID_PARAM_NICKNAME("1004", "입력한 닉네임이 올바르지 않습니다."),
    INVALID_PASSWORD_MATCH("1005", "비밀번호와 비밀번호 확인이 일치하지 않습니다."),
    INVALID_PASSWORD_FORMAT("1006", "비밀번호는 최소 8글자 이상, 최대 16글자 이하로 작성해야 하며, 비밀번호는 영어와 숫자를 혼용해야 하며, 공백은 사용할 수 없습니다."),
    INVALID_PROVIDER("1007", "회원가입 경로를 확인해주세요.(이메일, 소셜)"),
    INVALID_PASSWORD("1008","비밀번호가 일치하지 않습니다"),

    /**
     * Terms (1100 ~ 1200)
     */
    NOT_FOUND_TERMS("1100", "약관 정보가 존재하지 않습니다"),
    NOT_AGREED_MANDATORY_TERMS("1101", "필수 약관에 동의하지 않았습니다"),

    /**
     * Authentication (1200 ~ 1300)
     */
    INTERNAL_AUTHENTICATION_SERVICE("1200", "인증 서비스가 존재하지 않습니다."),
    NOT_FOUND_OAUTH_PROVIDER("1201", "가입경로(PROVIDER)를 찾을 수 없습니다."),
    NON_EXPIRED_ACCOUNT("1202", "사용자 계정이 탈퇴되었습니다."),
    NON_LOCKED_ACCOUNT("1203", "사용자 계정이 정지되었습니다."),
    DISABLE_ACCOUNT("1204", "사용자 계정은 비활성화 상태입니다."),
    EXPIRED_CREDENTIAL("1205", "사용자 인증 정보가 만료되었습니다."),

    /**
     * Json Web Token (1300 ~ 1400)
     */
    EXPIRED_JWT_EXCEPTION("1300", "만료된 토큰입니다."),
    UNSUPPORTED_JWT_EXCEPTION("1301", "지원되지 않는 토큰입니다."),
    MALFORMED_JWT_EXCEPTION("1302", "잘못된 형식의 토큰입니다."),
    SIGNATURE_EXCEPTION("1303", "토큰 서명이 올바르지 않습니다."),
    ILLEGAL_ARGUMENT_EXCEPTION("1304", "잘못된 인자가 전달되었습니다."),

    ;

    private final String code;
    private final String message;

}
