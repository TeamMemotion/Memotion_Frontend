package com.example.memotion.account.auth.signup;

import com.google.gson.annotations.SerializedName;

public class SignUpPostRequest {
    @SerializedName(value = "username") private String username;
    @SerializedName(value = "email") private String email;
    @SerializedName(value = "password") private String password;
    @SerializedName(value = "image") private String image;

    public SignUpPostRequest(String username, String email, String password, String image) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.image = image;
    }
}
