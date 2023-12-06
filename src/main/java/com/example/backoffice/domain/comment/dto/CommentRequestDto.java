package com.example.backoffice.domain.comment.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private Long comment_like_count;  // 댓글 좋아요 수
    private String texts;              // 댓글 내용
}
