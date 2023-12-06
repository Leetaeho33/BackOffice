package com.example.backoffice.domain.user.dto;

import com.example.backoffice.domain.user.entity.User;
import lombok.Getter;

@Getter
public class UpdateUserResponseDTO {
    String mbti;
    String intro;
    String password;
    public UpdateUserResponseDTO(User user){
        this.mbti = user.getMbti();
        this.intro = user.getIntro();
        this.password = user.getPassword();
    }
}
