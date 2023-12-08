package com.example.backoffice.domain.post.repository;

import com.example.backoffice.domain.post.entity.Post;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MbtiPostRepository {
    List<Post> findAllByMbti(String mbti);
}
