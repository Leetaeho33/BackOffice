package com.example.backoffice.domain.commentLike.service;

import com.example.backoffice.domain.comment.entity.Comment;
import com.example.backoffice.domain.comment.service.CommentService;
import com.example.backoffice.domain.commentLike.dto.LikeResponseDto;
import com.example.backoffice.domain.commentLike.entity.Likes;

import com.example.backoffice.domain.commentLike.repository.LikeRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



// 좋아요 서비스 클래스
// 댓글에 대한 좋아요 기능을 처리하는 서비스입니다.

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {

    private final CommentService commentService;  // 댓글 서비스
    private final LikeRepository likeRepository;  // 좋아요 레포지토리

    // 댓글에 대한 좋아요를 전환
    @Transactional
    public LikeResponseDto switchLike(Long comment_id) {

        // 댓글 서비스를 통해 댓글을 조회
        Comment comment = commentService.findComment(comment_id);

        // 댓글에 대한 좋아요 정보를 찾거나, 없으면 생성하여 가져옴
        Likes likes = likeRepository.findByComment(comment)
                .orElseGet(() -> saveCommentLike(comment));

        // 좋아요 정보를 전환하고, 해당 댓글의 좋아요 수를 갱신
        Boolean updated = likes.updateLike();
        comment.updateLikeCount(updated);

        // LikeResponseDto 생성자를 사용하여 좋아요 여부를 포함한 응답 객체 생성
        return new LikeResponseDto(likes.getIsLiked());
    }

    // 댓글에 대한 좋아요 정보를 저장
    @Transactional
    public Likes saveCommentLike(Comment comment) {

        // 댓글 정보를 기반으로 좋아요 엔티티를 생성
        Likes likes = Likes.builder()
                .comment(comment)
                .build();

        // 생성된 좋아요 엔티티를 저장하고 반환
        return likeRepository.save(likes);
    }

}
