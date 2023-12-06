package com.example.backoffice.domain.post.controller;

import com.example.backoffice.domain.post.dto.CreatePostRequestDto;
import com.example.backoffice.domain.post.dto.CreatePostResponseDto;
import com.example.backoffice.domain.post.service.PostService;
import com.example.backoffice.domain.user.entity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<CreatePostResponseDto> createPost(@RequestBody CreatePostRequestDto createPostRequestDto,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CreatePostResponseDto createPostResponseDto = postService.createPost(createPostRequestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(createPostResponseDto);
    }
}
