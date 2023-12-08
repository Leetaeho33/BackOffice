package com.example.backoffice.domain.user.controller;


import com.example.backoffice.domain.comment.dto.CommentResponseDto;
import com.example.backoffice.domain.post.dto.GetPostResponseDto;
import com.example.backoffice.domain.user.dto.*;
import com.example.backoffice.domain.user.entity.UserDetailsImpl;
import com.example.backoffice.domain.user.service.UserService;
import com.example.backoffice.global.common.CommonCode;
import com.example.backoffice.global.security.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final JwtUtil jwtUtil;

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignUpRequestDTO signUpRequestDTO){
        userService.signup(signUpRequestDTO);
        return ResponseEntity.ok(CommonCode.OK.getMessage());
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequestDTO,
                                                   HttpServletResponse response){
        userService.login(loginRequestDTO);
        response.setHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(loginRequestDTO.getUsername()));
        return ResponseEntity.ok(CommonCode.OK.getMessage());
    }

    @GetMapping
    public MypageResponseDTO mypage(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.getMypage(userDetails.getUser());
    }

    //관리자용 원하는 유저의 마이페이지 확인
    @GetMapping("/{userId}")
    public ResponseEntity<MypageResponseDTO> getUserPage (@PathVariable Long userId,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        MypageResponseDTO responseDto = userService.getUserPage(userId,userDetails.getUser());
        return ResponseEntity.ok(responseDto);
    }

    // 의논 사항 : 응답에 비밀번호를 응답할지? 응답하지 않을지? 지금은 응답하고 있습니다.
    @PatchMapping
    public UpdateUserResponseDTO updateProfile(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                               @RequestBody UpdateUserRequestDTO updateUserRequestDTO){
        return userService.updateUser(userDetails.getUser(), updateUserRequestDTO);
    }

    @PostMapping("/checkPwd")
    public ResponseEntity<String> checkPwd(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                           @RequestBody CheckPwdRequestDTO checkPwdRequestDTO){
        userService.checkPwd(userDetails.getUser(), checkPwdRequestDTO);
        return ResponseEntity.ok(CommonCode.OK.getMessage());
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails){
        userService.checkLogin(userDetails.getUser());
        session.invalidate();
        return ResponseEntity.ok(CommonCode.OK.getMessage());
    }

}
