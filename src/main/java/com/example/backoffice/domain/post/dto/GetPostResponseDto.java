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
public class GetPostResponseDto {

    private String title;
    private String content;
    private String mbti;
    private Long postLikeCnt;
    private Boolean isPostLiked;
    private LocalDateTime createdAt;
    private List<CommentResponseDto> commentList;

    @Builder
    private GetPostResponseDto(String title, String content, String mbti, Long postLikeCnt, Boolean isPostLiked,
                               LocalDateTime createdAt, List<CommentResponseDto> commentList) {
        this.title = title;
        this.content = content;
        this.mbti = mbti;
        this.postLikeCnt = postLikeCnt;
        this.isPostLiked = isPostLiked;
        this.createdAt = createdAt;
        this.commentList = commentList;
    }

    public static GetPostResponseDto of(Post post, Boolean isPostLiked, List<CommentResponseDto> responseDtos) {
        return GetPostResponseDto.builder()
            .title(post.getTitle())
            .content(post.getContent())
            .mbti(post.getUser().getMbti())
            .postLikeCnt(post.getPostLikeCnt())
            .isPostLiked(isPostLiked)
            .createdAt(post.getCreatedAt())
            .commentList(responseDtos)
            .build();
    }
}
