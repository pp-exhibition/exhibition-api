package com.pp.controller.member;

import com.pp.dto.request.SignUpRequest;
import com.pp.dto.response.SignUpResponse;
import com.pp.exception.ResponseCode;
import com.pp.response.CommonResponse;
import com.pp.service.member.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "회원 및 인증/인가 API",
        description = "회원 및 인증/인가 관련 API"
)
@RestController
@RequestMapping(value = {"/v1/api/auth", "/api/auth"})
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "이메일 회원가입",
            description = "이메일을 통한 회원가입을 진행한다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "회원 및 토큰 응답",
            content = @Content(schema = @Schema(implementation = SignUpResponse.class))
    )
    @PostMapping("/sign-up")
    public CommonResponse<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest request) {
        final SignUpResponse response = authService.signUp(request);
        return CommonResponse.success(ResponseCode.SUCCESS, response);
    }


}
