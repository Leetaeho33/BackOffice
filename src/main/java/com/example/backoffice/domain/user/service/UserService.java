package com.example.backoffice.domain.user.service;

import com.example.backoffice.domain.user.dto.*;
import com.example.backoffice.domain.user.entity.User;
import com.example.backoffice.domain.user.UserRepository;
import com.example.backoffice.domain.user.entity.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    User user;

    public void signup(SignUpRequestDTO signUpRequestDTO) {
        user = new User(signUpRequestDTO);
        Optional<User> userOptional = userRepository.findByUsername(user.getUsername());
        if (userOptional.isPresent()) {
            throw new IllegalArgumentException("중복된 회원이 존재합니다.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void login(LoginRequestDTO loginRequestDTO) {
        String username = loginRequestDTO.getUsername();
        String password = loginRequestDTO.getPassword();
        user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("등록된 유저가 없습니다."));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    public MypageResponseDTO getMypage(UserDetailsImpl userDetails) {
        if (userDetails != null) {
            user = userDetails.getUser();
        } else {
            throw new NullPointerException("로그인 된 회원이 아닙니다.");
        }
        return new MypageResponseDTO(user);
    }

    @Transactional
    public UpdateUserResponseDTO updateUser(UserDetailsImpl userDetails, UpdateUserRequestDTO updateUserRequestDTO) {
        if (userDetails != null) {
            user = userDetails.getUser();
        } else {
            throw new NullPointerException("로그인 된 회원이 아닙니다.");
        }
        user.updateUser(updateUserRequestDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return new UpdateUserResponseDTO(user);
    }

    public void checkPwd(UserDetailsImpl userDetails, CheckPwdRequestDTO checkPwdRequestDTO) {
        String password = checkPwdRequestDTO.getPassword();
        if (userDetails != null) {
            user = userDetails.getUser();
        } else {
            throw new NullPointerException("로그인 된 회원이 아닙니다.");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}
