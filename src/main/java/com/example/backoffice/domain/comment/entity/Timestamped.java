package com.example.backoffice.domain.comment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Timestamped {

    @CreatedDate  // 생성일자를 나타내는 어노테이션
    @Column(updatable = false)  // 업데이트 시에는 변경되지 않도록 설정
    @Temporal(TemporalType.TIMESTAMP)  // 데이터베이스에 저장되는 시간 형식 설정
    private LocalDateTime createdAt;  // 생성일자

    @LastModifiedDate  // 최종 수정일자를 나타내는 어노테이션
    @Column  // 기본적으로는 업데이트 시에 변경됨
    @Temporal(TemporalType.TIMESTAMP)  // 데이터베이스에 저장되는 시간 형식 설정
    private LocalDateTime modifiedAt;  // 최종 수정일자
}
