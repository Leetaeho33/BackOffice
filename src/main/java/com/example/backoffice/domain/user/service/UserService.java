package com.example.backoffice.domain.user.service;

import com.example.backoffice.domain.user.dto.*;
import com.example.backoffice.domain.user.entity.User;
import com.example.backoffice.domain.user.UserRepository;
import com.example.backoffice.domain.user.entity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.VisibleForTesting;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLOutput;
import java.util.Optional;

@Service
@RequiredArgsConstructor
// 서비스단에 들어오면 읽기만 한다.
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    User user;

    @Transactional
    public void signup(SignUpRequestDTO signUpRequestDTO) {
        String username = signUpRequestDTO.getUsername();
        String password = passwordEncoder.encode(signUpRequestDTO.getPassword());
        String mbti = signUpRequestDTO.getMbti();
        String intro = signUpRequestDTO.getIntro();

        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            throw new IllegalArgumentException("중복된 회원이 존재합니다.");
        }
        User user = User.builder().username(username).password(password)
                .mbti(mbti).intro(intro).build();
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

    public MypageResponseDTO getMypage(User requestsUser) {
        user = checkLogin(requestsUser);
        return new MypageResponseDTO(user);
    }

    @Transactional
    public UpdateUserResponseDTO updateUser(User requestsUser, UpdateUserRequestDTO updateUserRequestDTO) {
        user = checkLogin(requestsUser);
        user.updateUser(updateUserRequestDTO);
        user.setPassword(passwordEncoder.encode(updateUserRequestDTO.getPassword()));
        userRepository.save(user);
        return new UpdateUserResponseDTO(user);
    }

    public void checkPwd(User requestsUser, CheckPwdRequestDTO checkPwdRequestDTO) {
        String password = checkPwdRequestDTO.getPassword();
        user = checkLogin(requestsUser);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    public User checkLogin(User user){
        if (user == null) {
            throw new NullPointerException("로그인 된 회원이 아닙니다.");
        }
        return user;
    }
}
