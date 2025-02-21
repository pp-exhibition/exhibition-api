package com.pp.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppConstants {

    /**
     * FORMAT
     */
    public static final String DATE_FORMAT_YYYYMMDD = "yyyy/MM/dd";
    public static final String NUMBER_CHARACTERS = "0123456789";
    public static final String ALPHABET_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz9";
    public static final String ALPHABET_AND_NUMBER_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    public static final String SPECIAL_CHARACTERS = "!@#$%^&*_+-=";

    /**
     * PASSWORD
     */
    public static final int PASSWORD_MIN_LENGTH = 8;
    public static final int PASSWORD_MAX_LENGTH = 16;
    public static final String PASSWORD_PATTERN = "^(?=.*[a-zA-Z])(?=.*\\d)(?!.*\\s).+$";

    /**
     * FILE
     */
    public static final String LINE_SEPARATOR = System.lineSeparator();
    public static final String FILE_DOT = ".";

}
