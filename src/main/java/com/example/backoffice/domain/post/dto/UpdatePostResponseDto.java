package com.example.backoffice.domain.post.dto;

import com.example.backoffice.domain.post.entity.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdatePostResponseDto {

    private String title;
    private String content;
    private String username;
    private Long postLikeCnt;
    private Boolean isLiked;
    private LocalDateTime createdAt;

    @Builder
    private UpdatePostResponseDto(String title, String content, String username,
                                  Long postLikeCnt, Boolean isLiked, LocalDateTime createdAt) {
        this.title = title;
        this.content = content;
        this.username = username;
        this.postLikeCnt = postLikeCnt;
        this.isLiked = isLiked;
        this.createdAt = createdAt;
    }

    public static UpdatePostResponseDto of(Post post, Boolean isLiked) {
        return UpdatePostResponseDto.builder()
            .title(post.getTitle())
            .content(post.getContent())
            .username(post.getUser().getUsername())
            .postLikeCnt(post.getPostLikeCnt())
            .isLiked(isLiked)
            .createdAt(post.getCreatedAt())
            .build();
    }
}
