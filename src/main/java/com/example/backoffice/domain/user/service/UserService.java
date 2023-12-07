package com.example.backoffice.domain.user.service;


import com.example.backoffice.domain.user.dto.*;
import com.example.backoffice.domain.user.entity.PasswordHistory;
import com.example.backoffice.domain.user.entity.User;
import com.example.backoffice.domain.user.exception.AlreadyExistUserException;
import com.example.backoffice.domain.user.exception.NonUserExsistException;
import com.example.backoffice.domain.user.exception.PasswordIsNotMatchException;
import com.example.backoffice.domain.user.exception.RecentlySetPasswordException;
import com.example.backoffice.domain.user.repository.PasswordHistoryRepository;
import com.example.backoffice.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.example.backoffice.domain.user.exception.UserErrorCode.*;

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
            throw new AlreadyExistUserException(ALREADY_EXSIST_USER);
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
                .orElseThrow(() -> new NonUserExsistException(NON_USER_EXSIST));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new PasswordIsNotMatchException(PASSWORD_IS_NOT_MATCH);
        }
    }
    public MypageResponseDTO getMypage(User requestsUser) {
        user = checkLogin(requestsUser);
        return new MypageResponseDTO(user);
    }

    // 변경사항 : update시 repository에서 save 했던 코드 삭제
    @Transactional
    public UpdateUserResponseDTO updateUser(User requestsUser, UpdateUserRequestDTO updateUserRequestDTO) {
        user = checkLogin(requestsUser);
        user.updateUser(updateUserRequestDTO);
        updatePassword(user, updateUserRequestDTO);
        return new UpdateUserResponseDTO(user);
    }

    public void checkPwd(User requestsUser, CheckPwdRequestDTO checkPwdRequestDTO) {
        String password = checkPwdRequestDTO.getPassword();
        user = checkLogin(requestsUser);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new PasswordIsNotMatchException(PASSWORD_IS_NOT_MATCH);
        }
    }

    public User checkLogin(User user){
        if (user == null) {
            throw new NonUserExsistException(NON_LOGIN_USER);
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
                    throw new RecentlySetPasswordException(RECNTLY_SET_PASSWORD);
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
