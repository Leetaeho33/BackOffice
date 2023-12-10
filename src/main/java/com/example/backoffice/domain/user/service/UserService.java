package com.example.backoffice.domain.user.service;


import com.example.backoffice.domain.comment.dto.CommentResponseDto;
import com.example.backoffice.domain.comment.entity.Comment;
import com.example.backoffice.domain.post.dto.GetPostResponseDto;
import com.example.backoffice.domain.post.entity.Post;
import com.example.backoffice.domain.post.exception.PostErrorCode;
import com.example.backoffice.domain.post.exception.PostExistException;
import com.example.backoffice.domain.user.dto.*;
import com.example.backoffice.domain.user.entity.PasswordHistory;
import com.example.backoffice.domain.user.entity.User;
import com.example.backoffice.domain.user.entity.UserRoleEnum;
import com.example.backoffice.domain.user.exception.*;
import com.example.backoffice.domain.user.repository.PasswordHistoryRepository;
import com.example.backoffice.domain.user.repository.UserRepository;
import com.example.backoffice.global.security.JwtUtil;
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

//    private final RedisUtil redisUtil;
    private final JwtUtil jwtUtil;
    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "12345";
    User user;


    @Transactional
    public void signup(SignUpRequestDTO signUpRequestDTO) {
        String username = signUpRequestDTO.getUsername();
        String password = passwordEncoder.encode(signUpRequestDTO.getPassword());
        String mbti = signUpRequestDTO.getMbti();
        String intro = signUpRequestDTO.getIntro();

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER; // 기본적으로 사용자로 초기화

        // 만약 회원가입 요청이 관리자인 경우
        if (signUpRequestDTO.isAdmin()) {
            // 만약 관리자 토큰이 기대하는 토큰과 일치하지 않으면 예외를 던짐
            if (!ADMIN_TOKEN.equals(signUpRequestDTO.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            // 관리자 토큰이 일치하면 사용자 역할을 관리자로 변경
            role = UserRoleEnum.ADMIN;
        }


        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            throw new AlreadyExistUserException(ALREADY_EXSIST_USER);
        }
        User user = User.builder()
                .username(username)
                .password(password)
                .mbti(mbti)
                .intro(intro)
                .role(role)// 사용자 역할(Role) 설정 (기본값은 USER, 관리자인 경우 ADMIN으로 설정될 수 있음)
                .build();

        PasswordHistory passwordHistory = PasswordHistory.builder().
                password(password)
                .user(user)
                .build();

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
//        String refreshToken = jwtUtil.createRefreshToken();
//        refreshToken = refreshToken.split(" ")[1].trim();
//        redisUtil.set(refreshToken, user.getId(), 60 * 24 * 14);
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



    public MypageResponseDTO getUserPage(Long userId, User requestsUser) {
        // 사용자의 역할 확인
        checkUserRole(requestsUser);

        // 특정 사용자 식별자로 사용자 조회
        user = findById(userId);

        // 조회된 사용자 정보를 MypageResponseDTO로 변환하여 반환
        return new MypageResponseDTO(user);
    }


    @Transactional
    public Long deleteUser(Long userId, User requestingUser) {
        // 사용자의 역할 확인
        checkUserRole(requestingUser);

        // 특정 사용자 식별자로 사용자 조회
        User userToDelete = findById(userId);

        // 댓글 삭제
        userRepository.delete(userToDelete);

        return userId;
    }


    // 특정 사용자 식별자로 사용자를 조회하는 메소드.
    //userId 사용자 식별자
    //NonUserExsistException 사용자가 존재하지 않을 경우 발생하는 예외
    private User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new NonUserExsistException(UserErrorCode.NON_USER));
    }

    //사용자의 역할을 확인하여 특정 권한이 없으면 예외를 발생시키는 메소드.
    //user 확인할 사용자 객체
    //PostExistException 권한이 없을 경우 발생하는 예외\
    private void checkUserRole(User user) {
        if (user.getRole().equals(UserRoleEnum.USER)) {
            throw new PostExistException(PostErrorCode.NO_AUTHORITY);
        }
    }

}
