package com.pp.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Duration;

@SuppressWarnings("java:S2386")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityConstants {

    public static final String CONTENT_TYPE = "application/json; charset=utf-8";

    public static final String[] ALLOW_ORIGINS = {
            "http://localhost:8080",
            "http://localhost:3000",
    };

    public static final String[] SWAGGER_PATH = {
            "/swagger-ui/**",
            "/api-docs/**",
    };

    public static final String[] WEB_IGNORE_PATH = {
            "/health-check",
    };

    public static final String[] PERMIT_ALL_PATH = {
            "/health-check",

            /**
             * Member
            */
            "/api/auth/**"
    };

    public static final String[] PERMIT_ADMIN_PATH = {

    };

    public static final String[] PERMIT_USER_PATH = {

    };

    public static final String JWT_SUBJECT_SEPARATOR = "::";
    public static final Long ACCESS_TOKEN_EXPIRE_TIME = Duration.ofHours(3).toMillis();

}
