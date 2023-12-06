package com.example.backoffice.user.dto;

import com.example.backoffice.user.entity.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MypageResponseDTO {
    String mbti;
    String intro;

    public MypageResponseDTO(User user){
        this.mbti = user.getMbti();
        this.intro = user.getIntro();
    }
}
