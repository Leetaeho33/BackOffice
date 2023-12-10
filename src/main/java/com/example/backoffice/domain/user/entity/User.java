package com.example.backoffice.domain.user.entity;

import com.example.backoffice.domain.user.dto.SignUpRequestDTO;

import com.example.backoffice.domain.user.dto.UpdateUserRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.sql.Update;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;


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

    @Column(nullable = false)// null 값이 허용되지 않음
    @Enumerated(value = EnumType.STRING)//Enum 상수를 문자열로 저장
    private UserRoleEnum role; // 사용자 역할(UserRoleEnum)


    @Column
    private String intro;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<PasswordHistory> passwordHistoryList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<PasswordHistory> delPasswordHistoryList;



    public void updateUser(UpdateUserRequestDTO userRequestDTO){
        this.intro = userRequestDTO.getIntro();
        this.mbti = userRequestDTO.getMbti();
        this.password = userRequestDTO.getPassword();
    }
    public void setPassword(String password){
        this.password = password;
    }
    @Builder
    private User (String username, String password, String mbti, String intro, UserRoleEnum role){
        this.username = username;
        this.password = password;
        this.mbti = mbti;
        this.intro = intro;
        this.role = role;
    }

}
