package com.example.backoffice.domain.comment.entity;

import com.example.backoffice.domain.comment.dto.CommentRequestDto;
import com.example.backoffice.domain.commentLike.entity.Likes;
import com.example.backoffice.domain.post.entity.Post;
import com.example.backoffice.domain.utils.BaseTime;
import com.example.backoffice.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


//엔티티 클래스: 댓글 정보를 나타내는 엔티티

@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor
public class Comment extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long commentLikeCount;

    @Column(name = "texts", nullable = false, length = 500)
    private String texts;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    List<Likes> likesList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;


//    엔티티의 빌더 클래스
//    id 댓글 식별자
//    texts 댓글 내용
//    user 작성자 정보
//    post 댓글이 달린 게시물 정보
    @Builder
    private Comment(Long id, String texts, User user, Post post) {
        this.commentLikeCount = 0L;
        this.id = id;
        this.texts = texts;
        this.user = user;
        this.post = post;
    }


//     댓글 내용을 업데이트하는 메소드
//     requestDto 댓글 수정 요청 DTO

    public void update(CommentRequestDto requestDto) {
        this.texts = requestDto.getTexts();
    }


//    댓글 좋아요 수를 업데이트하는 메소드
//    updated 업데이트 여부를 나타내는 값 (true: 증가, false: 감소)

    public void updateLikeCount(Boolean updated) {
        if (updated) {
            this.commentLikeCount++;
        } else {
            this.commentLikeCount--;
        }
    }
}
