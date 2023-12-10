package com.example.backoffice.domain.postLike.repository;

import com.example.backoffice.domain.post.entity.Post;
import com.example.backoffice.domain.postLike.entity.PostLike;
import com.example.backoffice.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    Optional<PostLike> findByPostAndUser(Post post, User user);
}
