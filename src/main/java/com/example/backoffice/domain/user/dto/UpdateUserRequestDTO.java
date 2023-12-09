package com.example.backoffice.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateUserRequestDTO {

    @Size(min= 4, max= 4, message = "mbti는 4글자 입니다.")
    String mbti;

    @Size(min= 1, max= 15, message = "한줄 소개의 길이는 1~15글자 사이입니다.")
    String intro;

    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$", message = "비밀번호는 소문자, 대문자, 숫자로만 이루어진 8~15글자로 입력해주세요.")
    String password;
}
