package com.pp.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignInRequest(
        @Schema(description = "이메일", example = "hello@gmail.com")
        @Email(message = "이메일 주소 형식이 잘못되었습니다.")
        String email,

        @Schema(description = "비밀번호", example = "1234qwer!@")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?!.*\\s).+$"
                , message = "비밀번호는 영어와 숫자를 혼용해야 하며 공백은 사용할 수 없습니다.")
        @Size(min = 8, max = 16, message = "비밀번호는 최소 8글자 이상 최대 16글자 이하로 작성해야 합니다.")
        String password
) {
}
