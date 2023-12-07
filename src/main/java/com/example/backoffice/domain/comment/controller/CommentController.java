package com.example.backoffice.domain.comment.controller;

import com.example.backoffice.domain.comment.dto.CommentRequestDto;
import com.example.backoffice.domain.comment.dto.CommentResponseDto;
import com.example.backoffice.domain.comment.service.CommentService;
import com.example.backoffice.domain.user.entity.UserDetailsImpl;
import com.example.backoffice.global.common.CommonCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 컨트롤러: 댓글 관련 API를 처리하는 컨트롤러 클래스
 */
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

//    CommentController 생성자
//    commentService 댓글 서비스 객체

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

//    댓글 생성 API
//    requestDto 댓글 생성 요청 DTO
//    userDetail 현재 인증된 사용자의 UserDetails 객체
//    HTTP 응답: 성공 시 메시지

    @PostMapping
    public ResponseEntity<String> createComment(@RequestBody CommentRequestDto requestDto,
                                                @AuthenticationPrincipal UserDetailsImpl userDetail) {
        commentService.createComment(requestDto, userDetail.getUser());
        return ResponseEntity.ok(CommonCode.OK.getMessage());
    }


//     모든 댓글 조회 API
//     userDetail 현재 인증된 사용자의 UserDetails 객체
//     모든 댓글에 대한 응답 DTO 리스트

    @GetMapping
    public List<CommentResponseDto> getComments(@AuthenticationPrincipal UserDetailsImpl userDetail) {
        return commentService.getComments(userDetail.getUser());
    }

//     댓글 수정 API
//     commentId 수정할 댓글의 식별자
//     requestDto 수정할 내용을 담은 DTO
//     userDetail 현재 인증된 사용자의 UserDetails 객체
//     HTTP 응답: 성공 시 메시지

    @PutMapping("/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable Long commentId,
                                                @RequestBody CommentRequestDto requestDto,
                                                @AuthenticationPrincipal UserDetailsImpl userDetail) {
        commentService.updateComment(commentId, requestDto, userDetail.getUser());
        return ResponseEntity.ok(CommonCode.OK.getMessage());
    }

//     댓글 삭제 API
//     commentId 삭제할 댓글의 식별자
//     userDetail 현재 인증된 사용자의 UserDetails 객체
//     HTTP 응답: 성공 시 메시지
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId,
                                                @AuthenticationPrincipal UserDetailsImpl userDetail) {
        commentService.deleteComment(commentId, userDetail.getUser());
        return ResponseEntity.ok(CommonCode.OK.getMessage());
    }
}
