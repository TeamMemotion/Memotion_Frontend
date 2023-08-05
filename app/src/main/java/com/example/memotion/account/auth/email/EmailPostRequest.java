package com.example.memotion.account.auth.email;

import com.google.gson.annotations.SerializedName;

public class EmailPostRequest {
    @SerializedName(value = "email") private String email;

    public EmailPostRequest(String email) {
        this.email = email;
    }
}
