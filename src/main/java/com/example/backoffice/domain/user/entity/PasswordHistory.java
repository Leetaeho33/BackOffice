package com.example.backoffice.domain.user.entity;

import com.example.backoffice.domain.utils.BaseTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Queue;

@Entity
@Table(name = "password_history")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordHistory extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

}
