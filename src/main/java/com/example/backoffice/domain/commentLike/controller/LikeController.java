package com.example.backoffice.domain.commentLike.controller;

import com.example.backoffice.domain.commentLike.dto.LikeResponseDto;
import com.example.backoffice.domain.commentLike.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments/{comment_Id}/like")
public class LikeController {

    private final LikeService likeService;

    //댓글 좋아요 스위치
    @PatchMapping
    public LikeResponseDto like(@PathVariable Long comment_Id) {
        return likeService.switchLike(comment_Id);
    }
}
