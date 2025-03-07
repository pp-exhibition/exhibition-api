package com.pp.dto.response;

import com.pp.entity.member.Member;
import com.pp.vo.Token;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

public record SignUpResponse(
        @Schema(description = "회원 고유 번호", example = "4")
        Long memberId,
        @Schema(description = "토큰", example = "_qAxe~")
        String accessToken,
        @Schema(description = "회원 고유 번호", example = "2024-06-01")
        Date accessExpiredAt
) {

    public static SignUpResponse from(Member member, Token token) {
        return new SignUpResponse(member.getId(), token.accessToken(), token.accessExpiredAt());
    }

}
