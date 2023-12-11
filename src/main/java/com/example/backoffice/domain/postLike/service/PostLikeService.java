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
public class PostLikeService {

    private final PostService postService;
    private final PostLikeRepository postLikeRepository;

    @Transactional
    public ResponsePostLikeDto switchLikePost(Long postId, User user) {
        Post post = postService.findById(postId);
        PostLike postLike = postLikeRepository.findByUserAndPost(user, post)
            .orElseGet(() -> savePostLike(post, user));

        Boolean updated = postLike.clickPostLike();
        post.updatePostLikeCnt(updated);

        return ResponsePostLikeDto.of(postLike.getIsPostLiked());
    }

    @Transactional
    public PostLike savePostLike(Post post, User user) {

        // 게시글 정보를 기반으로 좋아요 엔티티를 생성
        PostLike postLike = PostLike.builder()
            .post(post)
            .user(user)
            .isPostLiked(DEFAULT_POST_LIKE)
            .build();

        // 생성된 좋아요 엔티티를 저장하고 반환
        return postLikeRepository.save(postLike);
    }

}
