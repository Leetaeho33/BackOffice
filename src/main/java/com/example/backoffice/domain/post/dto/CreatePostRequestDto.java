package com.example.backoffice.domain.post.dto;

import lombok.Getter;

@Getter
public class CreatePostRequestDto {
    private String title;
    private String content;
}
