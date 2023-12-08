package com.example.backoffice.domain.commentLike.controller;

import com.example.backoffice.domain.commentLike.dto.LikeResponseDto;
import com.example.backoffice.domain.commentLike.service.LikeService;
import com.example.backoffice.domain.user.entity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/comments/{commentId}/like")
public class LikeController {

    private final LikeService likeService;

    //댓글 좋아요 스위치
    @PatchMapping
    public ResponseEntity<LikeResponseDto> like(@PathVariable Long commentId,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        LikeResponseDto likeResponseDto = likeService.switchLike(commentId,userDetails.getUser());
        return ResponseEntity.ok(likeResponseDto);
    }
}
