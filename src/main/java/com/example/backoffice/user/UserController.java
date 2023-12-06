package com.example.backoffice.user;

import com.example.backoffice.global.common.CommonCode;
import com.example.backoffice.global.security.JwtUtil;
import com.example.backoffice.user.dto.LoginRequestDTO;
import com.example.backoffice.user.dto.MypageResponseDTO;
import com.example.backoffice.user.dto.SignUpRequestDTO;
import com.example.backoffice.user.entity.UserDetailsImpl;
import com.example.backoffice.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
        return userService.getMypage(userDetails);
    }

}