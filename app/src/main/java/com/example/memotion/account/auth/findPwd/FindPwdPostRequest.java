package com.example.memotion.account.auth.findPwd;

import com.google.gson.annotations.SerializedName;

public class FindPwdPostRequest {
    @SerializedName(value = "email") private String email;

    public FindPwdPostRequest(String email) {
        this.email = email;
    }
}
