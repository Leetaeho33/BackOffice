package com.example.backoffice.domain.comment.dto;

import com.example.backoffice.domain.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long id;                     // 댓글 ID
    private Long commentLikeCount;     // 댓글 좋아요 수
    private String contents;             // 댓글 내용
    private LocalDateTime createdAt;    // 댓글 생성일시
    private LocalDateTime modifiedAt;   // 댓글 수정일시

    // Comment 엔티티를 기반으로 하는 CommentResponseDto 생성자
    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.commentLikeCount = comment.getCommentLikeCount();
        this.contents = comment.getTexts();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
