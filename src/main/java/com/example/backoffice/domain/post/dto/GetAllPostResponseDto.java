package com.example.backoffice.domain.post.dto;

import com.example.backoffice.domain.post.entity.Post;
import jakarta.persistence.PostPersist;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetAllPostResponseDto {

    private String title;
    private String content;
    private String mbti;
    private Long postLikeCnt;
    private Boolean isPostLiked;
    private LocalDateTime createdAt;

    @Builder
    private GetAllPostResponseDto(String title, String content, String mbti, Long postLikeCnt,
                                  Boolean isPostLiked, LocalDateTime createdAt) {
        this.title = title;
        this.content = content;
        this.mbti = mbti;
        this.postLikeCnt = postLikeCnt;
        this.isPostLiked = isPostLiked;
        this.createdAt = createdAt;
    }

    public static GetAllPostResponseDto of(Post post, Boolean isPostLiked) {
        return GetAllPostResponseDto.builder()
            .title(post.getTitle())
            .content(post.getContent())
            .mbti(post.getUser().getMbti())
            .postLikeCnt(post.getPostLikeCnt())
            .isPostLiked(isPostLiked)
            .createdAt(post.getCreatedAt())
            .build();
    }
}
