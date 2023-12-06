package com.example.backoffice.domain.post.entity;

import com.example.backoffice.domain.comment.entity.Comment;
import com.example.backoffice.domain.post.constant.PostLikeConstant;
import com.example.backoffice.domain.post.dto.PostUpdateRequsetDto;
import com.example.backoffice.domain.postLike.entity.PostLike;
import com.example.backoffice.domain.user.entity.User;
import com.example.backoffice.domain.utils.BaseTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "posts")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @NotNull
    private Long postLikeCnt = PostLikeConstant.DEFAULT_LIKE_CNT;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    List<Comment> commentList;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    List<PostLike> postLikeList = new ArrayList<>();

    @Builder
    private Post(Long id, String title, String content, Long postLikeCnt, User user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.postLikeCnt = postLikeCnt;
        this.user = user;
    }

    public void updatePost(PostUpdateRequsetDto postUpdateRequestDto) {
        this.title = postUpdateRequestDto.getTitle();
        this.content = postUpdateRequestDto.getContent();
    }

    public void updatePostLikeCnt(boolean updatedPostLike) {
        if (updatedPostLike) {
            this.postLikeCnt++;
            return;
        }
        this.postLikeCnt--;
    }

}