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

        @SerializedName("nickname")
        private String nickname;

        public KakaoLogin(String email, String image, String nickname) {
            this.email = email;
            this.image = image;
            this.nickname = nickname;
        }
    }
}
