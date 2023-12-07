package com.example.backoffice.domain.comment.dto;

import com.example.backoffice.domain.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long postId;
    private Long commentId;                     // 댓글 ID
    private String mbti;                 //mbti
    private Long commentLikeCount;     // 댓글 좋아요 수
    private String texts;             // 댓글 내용
    private LocalDateTime createdAt;    // 댓글 생성일시

    // Comment 엔티티를 기반으로 하는 CommentResponseDto 생성자
    public CommentResponseDto(Comment comment) {
        this.postId= comment.getPost().getId();
        this.commentId = comment.getId();
        this.mbti = comment.getUser().getMbti();
        this.commentLikeCount = comment.getCommentLikeCount();
        this.texts = comment.getTexts();
        this.createdAt = comment.getCreatedAt();
    }
}
