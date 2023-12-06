package com.example.backoffice.domain.comment.service;

import com.example.backoffice.domain.comment.dto.CommentRequestDto;
import com.example.backoffice.domain.comment.dto.CommentResponseDto;
import com.example.backoffice.domain.comment.entity.Comment;
import com.example.backoffice.domain.comment.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    // 댓글 생성
    public CommentResponseDto createComment(CommentRequestDto requestDto) {
        // RequestDto -> Entity
        Comment comment = new Comment(requestDto);

        // DB 저장
        Comment save = commentRepository.save(comment);

        // Entity -> ResponseDto
        CommentResponseDto commentResponseDto = new CommentResponseDto(save);

        return commentResponseDto;
    }

    //모든 댓글 조회 서비스 (좋아요 수 기준 내림차순 정렬)
    public List<CommentResponseDto> getComments() {
        // DB 조회
        return commentRepository.findAllByOrderByCommentLikeCountDesc().stream()
                .map(CommentResponseDto::new)
                .toList();
    }

    //댓글 업데이트
    @Transactional
    public Long updateComment(Long comment_id, CommentRequestDto requestDto) {
        // 해당 댓글이 DB에 존재하는지 확인
        Comment comment = findComment(comment_id);

        // 댓글 내용 수정
        comment.update(requestDto);

        return comment_id;
    }

    // 댓글 삭제
    public Long deleteComment(Long comment_id) {
        // 해당 댓글이 DB에 존재하는지 확인
        Comment comment = findComment(comment_id);

        // 댓글 삭제
        commentRepository.delete(comment);

        return comment_id;
    }

    // 댓글 조회
    public Comment findComment(Long comment_id) {
        return commentRepository.findById(comment_id)
                .orElseThrow(() -> new IllegalArgumentException("선택한 댓글은 존재하지 않습니다."));
    }
}
