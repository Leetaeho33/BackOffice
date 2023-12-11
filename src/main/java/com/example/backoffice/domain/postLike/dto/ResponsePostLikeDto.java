package com.example.backoffice.domain.postLike.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponsePostLikeDto {

    private Boolean isPostLiked;

    @Builder
    private ResponsePostLikeDto(Boolean isPostLiked) {
        this.isPostLiked = isPostLiked;
    }

    public static ResponsePostLikeDto of(Boolean isPostLiked) {
        return ResponsePostLikeDto.builder()
            .isPostLiked(isPostLiked)
            .build();
    }
}
