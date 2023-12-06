package com.example.backoffice.domain.user.repository;

import com.example.backoffice.domain.user.entity.PasswordHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory, Long> {
    List<PasswordHistory> findByUserId(Long id);
}
