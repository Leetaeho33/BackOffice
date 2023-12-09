package com.example.backoffice.domain.postLike.controller;


import com.example.backoffice.domain.post.dto.GetPostResponseDto;
import com.example.backoffice.domain.postLike.entity.PostLike;
import com.example.backoffice.domain.postLike.service.PostLikeService;
import com.example.backoffice.domain.user.entity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/like")
public class PostLikeController {

    private final PostLikeService postLikeService;
    @PatchMapping
    public ResponseEntity<GetPostResponseDto> likePost(@PathVariable Long postId,
                                                       @AuthenticationPrincipal UserDetailsImpl userDetails){
        GetPostResponseDto getPostResponseDto = postLikeService.switchLikePost(postId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK.value()).body(getPostResponseDto);
    }
}
