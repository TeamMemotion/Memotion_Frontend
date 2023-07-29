package com.example.memotion.account.login;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

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
