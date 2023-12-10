package com.example.backoffice.domain.postLike.service;

import com.example.backoffice.domain.comment.dto.CommentResponseDto;
import com.example.backoffice.domain.comment.entity.Comment;
import com.example.backoffice.domain.commentLike.entity.Likes;
import com.example.backoffice.domain.post.dto.GetPostResponseDto;
import com.example.backoffice.domain.post.entity.Post;
import com.example.backoffice.domain.post.exception.PostErrorCode;
import com.example.backoffice.domain.post.exception.PostExistException;
import com.example.backoffice.domain.post.repository.PostRepository;
import com.example.backoffice.domain.post.service.PostService;
import com.example.backoffice.domain.postLike.entity.PostLike;
import com.example.backoffice.domain.postLike.repository.PostLikeRepository;
import com.example.backoffice.domain.user.entity.User;
import com.example.backoffice.domain.user.entity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.backoffice.domain.comment.entity.QComment.comment;
import static com.example.backoffice.domain.postLike.constant.PostLikeConstant.DEFAULT_POST_LIKE;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostService postService;
    private final PostLikeRepository postLikeRepository;

    @Transactional
    public GetPostResponseDto switchLikePost(Long postId, User user) {
        Post post = postService.findById(postId);
        PostLike postLike = postLikeRepository.findByPostAndUser(post,user)
                .orElseGet(() -> savePostLike(post,user));

        Boolean updated = postLike.pressLike();
        post.updatePostLikeCnt(updated);


        List<CommentResponseDto> commentResponseDtoList = postService.commentList(post);

        return GetPostResponseDto.of(post ,updated,commentResponseDtoList);

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
