package com.example.backoffice.domain.user.service;

import com.example.backoffice.domain.user.UserRepository;
import com.example.backoffice.domain.user.entity.User;
import com.example.backoffice.domain.user.entity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl {
    private final UserRepository userRepository;

    public UserDetailsImpl getUserDetails(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("해당 유저를 찾을 수 없습니다." + username));
return new UserDetailsImpl(user);

    }
}
