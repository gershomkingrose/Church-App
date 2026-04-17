package com.church.app.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class AuthDtos {

    @Data
    public static class LoginRequest {
        @Email
        @NotBlank
        private String email;

        @NotBlank
        private String password;
    }

    @Data
    public static class RegisterRequest {
        @NotNull
        private Long tenantId;

        @NotBlank
        private String name;

        @Email
        @NotBlank
        private String email;

        @NotBlank
        private String password;

        private String role = "MEMBER";
    }

    @Data
    public static class AuthResponse {
        private String token;
        private Long userId;
        private Long tenantId;
        private String name;
        private String email;
        private String role;

        public AuthResponse(String token, Long userId, Long tenantId, String name, String email, String role) {
            this.token = token;
            this.userId = userId;
            this.tenantId = tenantId;
            this.name = name;
            this.email = email;
            this.role = role;
        }
    }
}
