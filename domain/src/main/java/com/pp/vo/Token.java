package com.pp.vo;


import java.util.Date;

public record Token(
        String accessToken,
        Date accessExpiredAt
) {

        public static Token of(String accessToken, Date accessExpiredAt) {
                return new Token(accessToken, accessExpiredAt);
        }

        public static Token emptyToken() {
                return new Token(null, null);
        }
}
