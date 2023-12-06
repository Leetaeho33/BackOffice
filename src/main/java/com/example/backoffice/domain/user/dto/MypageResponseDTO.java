package com.example.backoffice.domain.user.dto;

import com.example.backoffice.domain.user.entity.User;
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
