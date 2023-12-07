package com.example.backoffice.domain.post.dto;

import com.example.backoffice.domain.comment.dto.CommentResponseDto;
import com.example.backoffice.domain.post.entity.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelectPostResponseDto {

    private String title;
    private String content;
    private String username;
    private Long postLikeCnt;
    private Boolean isPostLiked;
    private LocalDateTime createdAt;
    private List<CommentResponseDto> commentList;

    @Builder
    private SelectPostResponseDto(String title, String content, String username, Long postLikeCnt, Boolean isPostLiked,
                                  LocalDateTime createdAt, List<CommentResponseDto> commentList) {
        this.title = title;
        this.content = content;
        this.username = username;
        this.postLikeCnt = postLikeCnt;
        this.isPostLiked = isPostLiked;
        this.createdAt = createdAt;
        this.commentList = commentList;
    }

    public static SelectPostResponseDto of(Post post, Boolean isPostLiked, List<CommentResponseDto> responseDtos) {
        return SelectPostResponseDto.builder()
            .title(post.getTitle())
            .content(post.getContent())
            .username(post.getUser().getUsername())
            .postLikeCnt(post.getPostLikeCnt())
            .isPostLiked(isPostLiked)
            .createdAt(post.getCreatedAt())
            .commentList(responseDtos)
            .build();
    }
}
