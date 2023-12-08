package com.example.backoffice.domain.user.entity;

// 사용자 역할을 정의하는 Enum 클래스
public enum UserRoleEnum {
    USER(Authority.USER),  // 사용자 권한을 나타내는 Enum 상수, 권한 문자열은 "ROLE_USER"
    ADMIN(Authority.ADMIN);  // 관리자 권한을 나타내는 Enum 상수, 권한 문자열은 "ROLE_ADMIN"

    private final String authority; // Enum 상수에 대한 권한 문자열

    // Enum 생성자, 권한 문자열을 초기화
    UserRoleEnum(String authority) {
        this.authority = authority;
    }

    // 권한 문자열을 반환하는 메서드
    public String getAuthority() {
        return this.authority;
    }

    // 권한 문자열을 정의하는 내부 클래스
    public static class Authority {
        public static final String USER = "ROLE_USER";   // 사용자 권한을 나타내는 문자열
        public static final String ADMIN = "ROLE_ADMIN"; // 관리자 권한을 나타내는 문자열
    }
}
