package com.example.backoffice.domain.user.dto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Builder

public class LoginRequestDTO {

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "사용자 아이디는 소문자와 숫자로만 이루어져있는 4~10글자로 입력해주세요.")
    String username;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$", message = "비밀번호는 소문자, 대문자, 숫자로만 이루어진 8~15글자로 입력해주세요.")
    String password;
}
