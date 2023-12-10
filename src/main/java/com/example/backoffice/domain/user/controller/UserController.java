package com.example.backoffice.domain.user.controller;


import com.example.backoffice.domain.comment.dto.CommentResponseDto;
import com.example.backoffice.domain.post.dto.GetPostResponseDto;
import com.example.backoffice.domain.user.dto.*;

import com.example.backoffice.domain.user.entity.UserDetailsImpl;
import com.example.backoffice.domain.user.service.UserService;
import com.example.backoffice.global.common.CommonCode;
import com.example.backoffice.global.common.CommonResponseDTO;
import com.example.backoffice.global.security.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final JwtUtil jwtUtil;

    private final UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDTO> signup(@Validated @RequestBody SignUpRequestDTO signUpRequestDTO){
        userService.signup(signUpRequestDTO);
        return ResponseEntity.status(HttpStatus.OK.value()).
                body(new CommonResponseDTO("회원가입 성공", HttpStatus.OK.value()));
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponseDTO> login(@Validated @RequestBody LoginRequestDTO loginRequestDTO,
                                                   HttpServletResponse response){
        userService.login(loginRequestDTO);
        response.setHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(loginRequestDTO.getUsername()));
        return ResponseEntity.status(HttpStatus.OK.value()).
                body(new CommonResponseDTO("로그인 성공", HttpStatus.OK.value()));
    }

    @GetMapping
    public MypageResponseDTO mypage(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.getMypage(userDetails.getUser());
    }



    // 의논 사항 : 응답에 비밀번호를 응답할지? 응답하지 않을지? 지금은 응답하고 있습니다.
    @PatchMapping
    public UpdateUserResponseDTO updateProfile(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                               @Validated @RequestBody UpdateUserRequestDTO updateUserRequestDTO){
        return userService.updateUser(userDetails.getUser(), updateUserRequestDTO);
    }

    @PostMapping("/checkPwd")
    public ResponseEntity<CommonResponseDTO> checkPwd(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                           @Validated @RequestBody CheckPwdRequestDTO checkPwdRequestDTO){
        userService.checkPwd(userDetails.getUser(), checkPwdRequestDTO);
        return ResponseEntity.status(HttpStatus.OK.value()).
                body(new CommonResponseDTO("비밀번호 확인 완료, 회원 정보 수정 페이지로", HttpStatus.OK.value()));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails){
        userService.checkLogin(userDetails.getUser());
//        session.invalidate();
        return ResponseEntity.ok(CommonCode.OK.getMessage());
    }

    //관리자용 원하는 유저의 마이페이지 확인
    @GetMapping("/{userId}")
    public ResponseEntity<MypageResponseDTO> getUserPage (@PathVariable Long userId,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        MypageResponseDTO responseDto = userService.getUserPage(userId,userDetails.getUser());
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId,
                                                @AuthenticationPrincipal UserDetailsImpl userDetail) {
        userService.deleteUser(userId, userDetail.getUser());
        return ResponseEntity.ok(CommonCode.OK.getMessage());
    }

}
