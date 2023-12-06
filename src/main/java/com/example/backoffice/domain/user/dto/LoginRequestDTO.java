package com.example.backoffice.domain.user.dto;



import lombok.Builder;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Builder

public class LoginRequestDTO {
    String username;
    String password;
}
