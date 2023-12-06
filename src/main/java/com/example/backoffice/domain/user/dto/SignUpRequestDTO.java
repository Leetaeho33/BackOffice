package com.example.backoffice.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class SignUpRequestDTO {
    String username;
    String password;
    String mbti;
    String intro;

}
