package com.example.memotion.mypage.patch.profile;

import com.google.gson.annotations.SerializedName;

import okhttp3.MultipartBody;

public class ProfilePatchRequest {
    @SerializedName(value = "password") private String password;

    public ProfilePatchRequest(String password) {
        this.password = password;
    }
}
