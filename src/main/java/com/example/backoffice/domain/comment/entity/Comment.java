package com.example.backoffice.domain.comment.entity;

import com.example.backoffice.domain.comment.dto.CommentRequestDto;
import com.example.backoffice.domain.commentLike.entity.Likes;
import com.example.backoffice.domain.post.entity.Post;
import com.example.backoffice.domain.user.entity.User;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity // JPA가 관리할 수 있는 Entity 클래스 지정
@Getter
@Setter
@Table(name = "comment") // 매핑할 테이블의 이름을 지정
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comment_id;  // 댓글 식별자

    private Long commentLikeCount;  // 댓글 좋아요 수

    @Column(name = "texts", nullable = false, length = 500)
    private String texts;  // 댓글 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    List<Likes> likesList = new ArrayList<>();  // 댓글에 연결된 좋아요 목록

    // 생성자: CommentRequestDto를 통해 댓글을 생성할 때 사용
    public Comment(CommentRequestDto requestDto) {
        this.commentLikeCount = 0L;
        this.texts = requestDto.getTexts();
    }

    // 댓글 내용을 갱신하는 메서드
    public void update(CommentRequestDto requestDto) {
        this.texts = requestDto.getTexts();
    }

    // 좋아요 갯수를 갱신하는 메서드
    public void updateLikeCount(Boolean updated) {
        if (updated) {
            this.commentLikeCount++;
            return;
        }
        this.commentLikeCount--;
    }
}
