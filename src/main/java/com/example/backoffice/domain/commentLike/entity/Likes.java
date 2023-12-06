// 좋아요 엔티티
// Builder 패턴을 사용하여 객체를 생성하고, 롬복 어노테이션을 이용하여 Getter 및 기본 생성자를 자동 생성합니다.
// updateLike 메서드를 통해 좋아요 상태를 업데이트합니다.

package com.example.backoffice.domain.commentLike.entity;

import com.example.backoffice.domain.comment.entity.Comment;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Likes {

    @Id
    @Column(name = "comment_like_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isLiked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @Builder
    private Likes(Long id, Comment comment, Boolean isLiked) {
        this.id = id;
        this.comment = comment;
        this.isLiked = false;
    }

    // 좋아요 상태를 업데이트
    public Boolean updateLike () {
        this.isLiked = !isLiked;
        return this.isLiked;
    }
}
