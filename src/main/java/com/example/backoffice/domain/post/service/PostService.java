package com.example.backoffice.domain.post.service;

import com.example.backoffice.domain.post.constant.PostConstant;
import com.example.backoffice.domain.post.dto.CreatePostRequestDto;
import com.example.backoffice.domain.post.dto.CreatePostResponseDto;
import com.example.backoffice.domain.post.entity.Post;
import com.example.backoffice.domain.post.exception.PostErrorCode;
import com.example.backoffice.domain.post.exception.PostExistException;
import com.example.backoffice.domain.post.repository.PostRepository;
import com.example.backoffice.domain.postLike.entity.PostLike;
import com.example.backoffice.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.backoffice.domain.post.constant.PostConstant.DEFAULT_LIKE_CNT;
import static com.example.backoffice.domain.postLike.constant.PostLikeConstant.DEFAULT_POST_LIKE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public CreatePostResponseDto createPost(CreatePostRequestDto createPostRequestDto, User user) {
        Post savePost = Post.builder()
            .title(createPostRequestDto.getTitle())
            .content(createPostRequestDto.getContent())
            .postLikeCnt(DEFAULT_LIKE_CNT)
            .user(user)
            .build();

        postRepository.save(savePost);

        return CreatePostResponseDto.of(savePost, getPostLiked(user, savePost));
    }

    private Post findById(Long postId) {
        return postRepository.findById(postId).orElseThrow(
            () -> new PostExistException(PostErrorCode.NO_POST));
    }

    private Boolean getPostLiked(User user, Post post) {
        return post.getPostLikeList()
            .stream()
            .filter(postLike -> postLike.getUser().getId().equals(user.getId()))
            .findFirst()
            .map(PostLike::getIsLiked)
            .orElse(DEFAULT_POST_LIKE);
    }
}
