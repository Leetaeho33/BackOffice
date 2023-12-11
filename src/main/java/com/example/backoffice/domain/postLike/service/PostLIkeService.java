package com.example.backoffice.domain.postLike.service;

import com.example.backoffice.domain.post.entity.Post;
import com.example.backoffice.domain.post.service.PostService;
import com.example.backoffice.domain.postLike.dto.ResponsePostLikeDto;
import com.example.backoffice.domain.postLike.entity.PostLike;
import com.example.backoffice.domain.postLike.repository.PostLikeRepository;
import com.example.backoffice.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.backoffice.domain.postLike.constant.PostLikeConstant.DEFAULT_POST_LIKE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostLIkeService {

    private final PostLikeRepository postLikeRepository;
    private final PostService postService;

    @Transactional
    public ResponsePostLikeDto clickPostLike(User user, Long postId) {
        Post post = postService.findById(postId);
        PostLike postLike = postLikeRepository.findByUserAndPost(user, post)
            .orElseGet(() -> savePostLike(user, post));

        boolean updated = postLike.clickPostLike();
        post.updatePostLikeCnt(updated);

        return ResponsePostLikeDto.of(postLike.getIsPostLiked());
    }

    @Transactional
    public PostLike savePostLike(User user, Post post) {
        PostLike postLike = PostLike.builder()
            .user(user)
            .post(post)
            .isPostLiked(DEFAULT_POST_LIKE)
            .build();

        return postLikeRepository.save(postLike);
    }
}
