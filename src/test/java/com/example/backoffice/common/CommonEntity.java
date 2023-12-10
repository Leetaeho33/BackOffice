package com.example.backoffice.common;

import com.example.backoffice.domain.user.entity.User;
import com.example.backoffice.domain.user.entity.UserRoleEnum;

public class CommonEntity {
    Long TEST_USER_ID = 1L;
    String TEST_USER_NAME = "test1";
    String TEST_ANOTHER_USER_NAME = "test2";

    String TEST_FIRST_PASSWORD = "12345678";
    String TEST_SECOND_PASSWORD = "12345679";
    String TEST_MBTI_INTP = "intp";
    String TEST_MBTI_ESFJ = "ESFJ";
    String TEST_FIRST_USER_INTRO = "첫번째 유저입니다.";
    String TEST_SECOND_USER_INTRO = "두번째 유저입니다.";
    UserRoleEnum ADMIN = UserRoleEnum.ADMIN;
    UserRoleEnum USER = UserRoleEnum.USER;

    public String TEST_UPDATE_MBTI = "intj";
    public String TEST_UPDATE_INTRO = "수정된 소개";
    public String TEST_NEW_PASSWORD = "123456710";
    public String TEST_DUPLICATED_PASSWORD = "12345678";


    User FIRST_USER = User.builder().
            username(TEST_USER_NAME).password(TEST_FIRST_PASSWORD).
            mbti(TEST_MBTI_INTP).intro(TEST_FIRST_USER_INTRO).role(USER).build();
    User ADMIN_USER = User.builder().
            username(TEST_ANOTHER_USER_NAME).password(TEST_SECOND_PASSWORD)
            .mbti(TEST_MBTI_ESFJ).intro(TEST_SECOND_USER_INTRO).role(ADMIN).build();

}
