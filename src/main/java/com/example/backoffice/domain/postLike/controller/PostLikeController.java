package com.example.backoffice.domain.postLike.controller;

import com.example.backoffice.domain.postLike.dto.ResponsePostLikeDto;
import com.example.backoffice.domain.postLike.service.PostLIkeService;
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
@RequestMapping("/api/posts/{postId}/postLikes")
public class PostLikeController {

    private final PostLIkeService postLIkeService;

    @PatchMapping
    public ResponseEntity<ResponsePostLikeDto> pressLike(@PathVariable Long postId,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ResponsePostLikeDto responsePostLikeDto = postLIkeService.clickPostLike(userDetails.getUser(), postId);

        return ResponseEntity.ok(responsePostLikeDto);
    }
}
