package com.pp.dto.response;

import com.pp.entity.member.Member;
import com.pp.vo.Token;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

public record SignInResponse(
        @Schema(description = "회원 고유 번호", example = "4")
        Long memberId,
        @Schema(description = "이메일", example = "hello@gmail.com")
        String email,
        @Schema(description = "닉네임", example = "가벼운해바라기wCj")
        String nickname,

        @Schema(description = "토큰", example = "_qAxe~")
        String accessToken,
        @Schema(description = "회원 고유 번호", example = "2024-06-01")
        Date accessExpiredAt
) {
        public static SignInResponse from(Member member, Token token) {
                return new SignInResponse(member.getId(), member.getEmail(), member.getNickname(), token.accessToken(), token.accessExpiredAt());
        }
}
