package com.example.backoffice.domain.user.dto;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class SignUpRequestDTO {

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "사용자 아이디는 소문자와 숫자로만 이루어져있는 4~10글자로 입력해주세요.")
    String username;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$", message = "비밀번호는 소문자, 대문자, 숫자로만 이루어진 8~15글자로 입력해주세요.")
    String password;

    @NotBlank(message = "mbti는 필수 입력 값입니다.")
    @Size(min= 4, max= 4, message = "mbti는 4글자 입니다.")
    String mbti;

    @Size(min= 1, max= 15, message = "한줄 소개의 길이는 1~15글자 사이입니다.")
    String intro;

    // 아래 변수는 관리자 토큰을 저장하는 문자열입니다.
    private String adminToken = "";

    // 아래 변수는 관리자 여부를 나타내는 불리언 값입니다.
    private boolean admin = false;


}
