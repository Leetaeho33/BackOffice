package com.example.backoffice.domain.comment.service;

import com.example.backoffice.domain.comment.dto.CommentRequestDto;
import com.example.backoffice.domain.comment.dto.CommentResponseDto;
import com.example.backoffice.domain.comment.entity.Comment;
import com.example.backoffice.domain.comment.exception.CommentErrorCode;
import com.example.backoffice.domain.comment.exception.CommentExistsException;
import com.example.backoffice.domain.comment.repository.CommentRepository;
import com.example.backoffice.domain.post.entity.Post;
import com.example.backoffice.domain.post.exception.PostErrorCode;
import com.example.backoffice.domain.post.exception.PostExistException;
import com.example.backoffice.domain.post.repository.PostRepository;
import com.example.backoffice.domain.user.entity.User;
import com.example.backoffice.domain.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

//댓글 관련 비즈니스 로직을 처리하는 서비스 클래스

@Service
@RequiredArgsConstructor
public class CommentService {
    private  final PostRepository postRepository;
    private final CommentRepository commentRepository;
//   댓글 생성 서비스
//   requestDto 생성할 댓글 정보를 담은 DTO
//   user 작성자 정보
//   생성된 댓글 정보를 담은 DTO

    public CommentResponseDto createComment(Long postId, CommentRequestDto requestDto, User user) {
        Post post = findById(postId);
        Comment comment = Comment.builder()
                .texts(requestDto.getTexts())
                .post(post)
                .user(user)
                .build();

        // DB 저장
        Comment savedComment = commentRepository.save(comment);

        // Entity -> ResponseDto
        CommentResponseDto commentResponseDto = new CommentResponseDto(savedComment);

        return commentResponseDto;
    }


//     모든 댓글 조회 서비스 (좋아요 수 기준 내림차순 정렬)
//     user 현재 로그인한 사용자 정보
//     조회된 댓글 정보 목록을 담은 DTO 리스트

//    public List<CommentResponseDto> getComments(User user) {
//
//        // DB 조회
//        return commentRepository.findAllByOrderByCommentLikeCountDesc().stream()
//                .map(CommentResponseDto::new)
//                .toList();
//    }


//    댓글 업데이트 서비스
//    commentId 업데이트할 댓글의 식별자
//    requestDto 업데이트할 댓글 정보를 담은 DTO
//    user 현재 로그인한 사용자 정보
//    업데이트된 댓글의 식별자

    @Transactional
    public Long updateComment(Long postId,Long commentId, CommentRequestDto requestDto, User user) {
        Comment comment = findComment(commentId);
        Post post = findById(postId);
        checkAuthorization(comment, user);

        // 댓글 내용 수정
        comment.update(requestDto);

        return commentId;
    }

//     댓글 삭제 서비스
//     commentId 삭제할 댓글의 식별자
//     user 현재 로그인한 사용자 정보
//     삭제된 댓글의 식별자

    public Long deleteComment(Long postId,Long commentId, User user) {
        // 해당 댓글이 DB에 존재하는지 확인
        Comment comment = findComment(commentId);
        Post post = findById(postId);
        checkAuthorization(comment, user);

        // 댓글 삭제
        commentRepository.delete(comment);

        return commentId;
    }


//   commentId 조회할 댓글의 식별자
//   조회된 댓글 엔티티

    public Comment findComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentExistsException(CommentErrorCode.NO_COMMENT));
    }

//    댓글 작성자 권한 확인 메소드
//    comment 확인할 댓글 엔티티
//    user 현재 로그인한 사용자 정보
//    CommentExistsException 작성자 권한이 없을 경우 발생하는 예외

    private void checkAuthorization(Comment comment, User user) {


        // 객체 동등성 비교
        if (!Objects.equals(comment.getUser().getId(),user.getRole().equals(UserRoleEnum.USER))) {
            throw new CommentExistsException(CommentErrorCode.UNAUTHORIZED_USER);
        }
    }

    private Post findById(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new PostExistException(PostErrorCode.NO_POST));
    }
}
