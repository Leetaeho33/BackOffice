package com.example.backoffice.domain.user.entity;

import com.example.backoffice.domain.user.dto.SignUpRequestDTO;
import com.example.backoffice.domain.user.dto.UpdateUserRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.sql.Update;

@Entity
@Table(name = "users")
@Getter
@Setter
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
    public void updateUser(UpdateUserRequestDTO userRequestDTO){
        this.intro = userRequestDTO.getIntro();
        this.mbti = userRequestDTO.getMbti();
        this.password = userRequestDTO.getPassword();
    }
}
