package com.example.backoffice.domain.comment.repository;

import com.example.backoffice.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 댓글을 좋아요 수를 기준으로 내림차순으로 정렬하여 모든 댓글을 가져옵니다.

    List<Comment> findAllByOrderByCommentLikeCountDesc();
}
