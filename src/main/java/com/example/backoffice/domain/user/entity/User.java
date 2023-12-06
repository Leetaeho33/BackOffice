package com.example.backoffice.domain.user.entity;

import com.example.backoffice.domain.user.dto.SignUpRequestDTO;
import com.example.backoffice.domain.user.dto.UpdateUserRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.sql.Update;
import org.springframework.security.crypto.password.PasswordEncoder;

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


    public void updateUser(UpdateUserRequestDTO userRequestDTO){
        this.intro = userRequestDTO.getIntro();
        this.mbti = userRequestDTO.getMbti();
        this.password = userRequestDTO.getPassword();
    }
    public void setPassword(String password){
        this.password = password;
    }
    @Builder
    private User (String username, String password, String mbti, String intro){
        this.username = username;
        this.password = password;
        this.mbti = mbti;
        this.intro = intro;
    }
}
