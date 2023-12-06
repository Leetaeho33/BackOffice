package com.example.backoffice.domain.post.dto;

import com.example.backoffice.domain.post.entity.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreatePostResponseDto {
    private String title;
    private String content;
    private String username;
    private Long postLikeCnt;
    private Boolean isHearted;
    private LocalDateTime createdAt;

    @Builder
    private CreatePostResponseDto(String title, String content, String username, Long postLikeCnt,
                                  Boolean isHearted, LocalDateTime createdAt) {
        this.title = title;
        this.content = content;
        this.username = username;
        this.postLikeCnt = postLikeCnt;
        this.isHearted = isHearted;
        this.createdAt = createdAt;
    }

    public static CreatePostResponseDto of(Post post, Boolean isHearted) {
        return CreatePostResponseDto.builder()
            .title(post.getTitle())
            .content(post.getContent())
            .username(post.getUser().getUsername())
            .postLikeCnt(post.getPostLikeCnt())
            .isHearted(isHearted)
            .createdAt(post.getCreatedAt())
            .build();
    }
}
