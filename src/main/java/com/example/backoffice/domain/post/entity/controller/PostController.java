package com.example.backoffice.domain.post.entity.controller;

import com.example.backoffice.domain.post.dto.*;
import com.example.backoffice.domain.post.service.PostService;
import com.example.backoffice.domain.user.entity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<CreatePostResponseDto> createPost(@RequestBody CreatePostRequestDto requestDto,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CreatePostResponseDto responseDto = postService.createPost(requestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<SelectPostResponseDto> getPost(@PathVariable Long postId,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        SelectPostResponseDto responseDto = postService.getPost(postId, userDetails.getUser());
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<UpdatePostResponseDto> updatePost(@PathVariable Long postId,
                                                            @RequestBody UpdatePostRequestDto requestDto,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UpdatePostResponseDto responseDto = postService.updatePost(postId, requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(responseDto);
    }
}
