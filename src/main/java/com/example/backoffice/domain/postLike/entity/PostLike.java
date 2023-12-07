package com.example.backoffice.domain.postLike.entity;

import com.example.backoffice.domain.post.entity.Post;
import com.example.backoffice.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean isPostLiked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    private PostLike(Long id, Boolean isPostLiked, User user, Post post) {
        this.id = id;
        this.isPostLiked = isPostLiked;
        this.user = user;
        this.post = post;
    }

    public Boolean pressLike() {
        this.isPostLiked = !isPostLiked;
        return this.isPostLiked;
    }
}
