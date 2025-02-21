package com.pp.utils;


import com.pp.constants.AppConstants;
import com.pp.exception.CustomException;
import com.pp.exception.ResponseCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RandomUtil {

    /**
     * 리스트에서 임의의 요소 반환합니다.
     *
     * @param list 리스트
     * @param <T> 목록의 요소 유형
     * @return 무작위로 선택된 요소
     */
    public static <T> T getRandomElement(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new CustomException(ResponseCode.INVALID_LIST);
        }
        int randomIndex = ThreadLocalRandom.current().nextInt(list.size());
        return list.get(randomIndex);
    }

    /**
     * 지정된 길이의 랜덤한 영문자 + 숫자로 구성된 문자열을 생성합니다.
     *
     * @param length 생성할 문자열의 길이
     * @return 영문자 및 숫자로 구성된 랜덤 문자열
     */
    public static String generateRandomCode(int length) {
        final String characters = AppConstants.ALPHABET_AND_NUMBER_CHARACTERS;
        return generateRandomString(length, characters);
    }

    /**
     * 지정된 길이의 랜덤한 숫자로 구성된 문자열을 생성합니다.
     *
     * @param length 생성할 문자열의 길이
     * @return 숫자로 구성된 랜덤 문자열
     */
    public static String generateRandomNumber(int length) {
        final String characters = AppConstants.NUMBER_CHARACTERS;
        return generateRandomString(length, characters);
    }

    /**
     * 주어진 문자 집합에서 지정된 길이의 랜덤 문자열을 생성합니다.
     *
     * @param length     생성할 문자열의 길이
     * @param characters 사용할 문자 집합 (랜덤 문자열 생성에 사용될 문자들)
     * @return 주어진 문자 집합에서 무작위로 선택된 문자들로 구성된 문자열
     */
    private static String generateRandomString(int length, String characters) {
        StringBuilder randomString = new StringBuilder(length);
        int charactersLength = characters.length();
        for (int i = 0; i < length; i++) {
            int randomIndex = ThreadLocalRandom.current().nextInt(charactersLength);
            randomString.append(characters.charAt(randomIndex));
        }
        return randomString.toString();
    }
}
