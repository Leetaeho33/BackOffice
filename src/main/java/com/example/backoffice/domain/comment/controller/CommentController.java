package com.example.backoffice.domain.comment.controller;

import com.example.backoffice.domain.comment.dto.CommentRequestDto;
import com.example.backoffice.domain.comment.dto.CommentResponseDto;
import com.example.backoffice.domain.comment.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    // CommentController 생성자
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 생성
    @PostMapping("/comments")
    public CommentResponseDto createComment(@RequestBody CommentRequestDto requestDto) {
        return commentService.createComment(requestDto);
    }

    // 모든 댓글 조회
    @GetMapping("/comments")
    public List<CommentResponseDto> getComments() {
        return commentService.getComments();
    }

    // 댓글 수정
    @PutMapping("/comments/{comment_id}")
    public Long updateComment(@PathVariable Long comment_id, @RequestBody CommentRequestDto requestDto) {
        return commentService.updateComment(comment_id, requestDto);
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{comment_id}")
    public Long deleteComment(@PathVariable Long comment_id) {
        return commentService.deleteComment(comment_id);
    }
}
