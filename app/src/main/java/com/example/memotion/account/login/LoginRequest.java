package com.example.memotion.account.login;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName(value = "email") private String email;

    @SerializedName(value = "password") private String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    static class KakaoLogin {
        @SerializedName("email")
        private String email;

        @SerializedName("image")
        private String image;

        @SerializedName("username")
        private String username;

        public KakaoLogin(String email, String image, String username) {
            this.email = email;
            this.image = image;
            this.username = username;
        }
    }
}
