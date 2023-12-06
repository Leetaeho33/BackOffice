package com.example.backoffice.domain.user.entity;

import com.example.backoffice.domain.user.dto.SignUpRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String mbti;

    @Column
    private String intro;

    public User (SignUpRequestDTO signUpRequestDTO){
        this.username = signUpRequestDTO.getUsername();
        this.password = signUpRequestDTO.getPassword();
        this.mbti = signUpRequestDTO.getMbti();
        this.intro = signUpRequestDTO.getIntro();
    }

}
