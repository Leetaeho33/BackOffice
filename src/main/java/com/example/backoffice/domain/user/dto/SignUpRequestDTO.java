package com.example.backoffice.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class SignUpRequestDTO {
    String username;
    String password;
    String mbti;
    String intro;

    // 아래 변수는 관리자 토큰을 저장하는 문자열입니다.
    private String adminToken = "";

    // 아래 변수는 관리자 여부를 나타내는 불리언 값입니다.
    private boolean admin = false;


}
