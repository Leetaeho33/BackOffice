package com.example.backoffice.domain.commentLike.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikeResponseDto {

    private Boolean isLiked;

    // 좋아요 응답 DTO 생성자

    @Builder
    public LikeResponseDto(Boolean isLiked) {
        this.isLiked = isLiked;
    }

}
