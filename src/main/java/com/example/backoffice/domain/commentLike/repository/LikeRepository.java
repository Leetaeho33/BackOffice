package com.example.backoffice.domain.commentLike.repository;


import com.example.backoffice.domain.comment.entity.Comment;
import com.example.backoffice.domain.commentLike.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Likes, Long> {

    //좋아요 엔티티에서 특정 댓글에 대한 좋아요 정보를 조회
    Optional<Likes> findByComment(Comment comment);
}
