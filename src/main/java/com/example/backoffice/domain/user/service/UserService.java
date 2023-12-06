package com.example.backoffice.domain.user.service;

import com.example.backoffice.domain.user.dto.*;
import com.example.backoffice.domain.user.entity.PasswordHistory;
import com.example.backoffice.domain.user.entity.User;
import com.example.backoffice.domain.user.repository.PasswordHistoryRepository;
import com.example.backoffice.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
// 서비스단에 들어오면 읽기만 한다.
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordHistoryRepository passwordHistoryRepository;
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
        PasswordHistory passwordHistory = PasswordHistory.builder().
                password(password).user(user).build();
        // 연관관계 맺었을 때 save 순서가 중요하다. 왜냐하면 passwordHistory 는 user_id를 가져야 하는데
        // user보다 먼저 save되면 user의 id를 몰라서 user_id를 가질 수 없다.
        userRepository.save(user);
        passwordHistoryRepository.save(passwordHistory);
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
        updatePassword(user, updateUserRequestDTO);
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
    public void updatePassword(User user, UpdateUserRequestDTO updateUserRequestDTO){

        if(!passwordEncoder.matches(updateUserRequestDTO.getPassword(), user.getPassword())){

            PasswordHistory passwordHistory = PasswordHistory.builder().user(user).
                    password(passwordEncoder.encode(updateUserRequestDTO.getPassword())).build();

            List<PasswordHistory> passwordHistoryList =
                    passwordHistoryRepository.findByUserId(user.getId());
            for(int i = 0;i<passwordHistoryList.size();i++){
                if(passwordEncoder.matches(updateUserRequestDTO.getPassword(),
                        passwordHistoryList.get(i).getPassword())){
                    throw new IllegalArgumentException("최근 3번안에 사용한 비밀번호는 사용할 수 없도록 제한합니다.");
                }
            }
            passwordHistoryRepository.save(passwordHistory);
            if(passwordHistoryList.size()>=3){
                PasswordHistory lastPasswordHistory = passwordHistoryList.get(0);
                passwordHistoryRepository.delete(lastPasswordHistory);
                user.setPassword(passwordEncoder.encode(updateUserRequestDTO.getPassword()));
            }
        }
    }
}
