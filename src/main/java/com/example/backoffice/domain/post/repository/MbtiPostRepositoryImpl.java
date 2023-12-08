package com.example.backoffice.domain.post.repository;

import com.example.backoffice.domain.post.entity.Post;
import com.example.backoffice.domain.post.entity.QPost;
import com.example.backoffice.domain.user.entity.QUser;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MbtiPostRepositoryImpl implements MbtiPostRepository {

    private final JPAQueryFactory queryFactory;

    public List<Post> findAllByMbti(String mbti) {
        QPost mbtiPost = QPost.post;
        QUser user = QUser.user;

        return queryFactory.selectFrom(mbtiPost)
            .where(mbtiPost.user.mbti.in(
                    JPAExpressions
                        .select(user.mbti)
                        .from(user)
                        .where(user.mbti.contains(mbti))
                )
            )
            .fetch();
    }
}
