package com.example.backoffice.domain.post.service;

import com.example.backoffice.domain.comment.dto.CommentResponseDto;
import com.example.backoffice.domain.comment.entity.Comment;
import com.example.backoffice.domain.post.dto.*;
import com.example.backoffice.domain.post.entity.Post;
import com.example.backoffice.domain.post.exception.PostErrorCode;
import com.example.backoffice.domain.post.exception.PostExistException;
import com.example.backoffice.domain.post.repository.PostRepository;
import com.example.backoffice.domain.postLike.entity.PostLike;
import com.example.backoffice.domain.user.entity.User;
import com.example.backoffice.domain.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    public UpdatePostResponseDto updatePost(Long postId, UpdatePostRequestDto requestDto, User user) {
        Post post = findById(postId);
        findByIdUsername(post, user);
        post.updatePost(requestDto);

        return UpdatePostResponseDto.of(post, getPostLiked(user, post));
    }

    public GetPostResponseDto getPost(Long postId, User user) {
        Post post = findById(postId);
        Boolean isPostLiked = getPostLiked(user, post);
        List<CommentResponseDto> commentResponseDtoList = commentList(post);

        return GetPostResponseDto.of(post, isPostLiked, commentResponseDtoList);
    }

    public List<GetAllPostResponseDto> getPostList(User user) {
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();
        return postList.stream()
            .map(post -> GetAllPostResponseDto.of(post, getPostLiked(user, post)))
            .collect(Collectors.toList());
    }







    private List<CommentResponseDto> commentList(Post post) {
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        List<Comment> commentList = post.getCommentList();
        commentList.sort((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));
        for (Comment comment : commentList) {
            commentResponseDtoList.add(
                new CommentResponseDto(comment));
        }
        return commentResponseDtoList;
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
            .map(PostLike::getIsPostLiked)
            .orElse(DEFAULT_POST_LIKE);
    }

    private void findByIdUsername(Post post, User user) {
        if (!post.getUser().getUsername().equals(user.getUsername())&&user.getRole().equals(UserRoleEnum.USER)) {
            throw new PostExistException(PostErrorCode.NO_AUTHORITY);
        }
    }
}
