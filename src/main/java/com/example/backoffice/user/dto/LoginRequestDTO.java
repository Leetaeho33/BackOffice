package com.example.backoffice.user.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class LoginRequestDTO {
    String username;
    String password;
}
