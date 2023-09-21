package com.example.memotion.mypage.get.profile;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetProfileResponse {
    @SerializedName(value = "isSuccess") private Boolean isSuccess;
    @SerializedName(value = "code") private int code;
    @SerializedName(value = "message") private String message;
    @SerializedName(value = "result") private Result result;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Result getResult() {
        return result;
    }

    public static class Result {
        @SerializedName(value = "memberId") private Long memberId;
        @SerializedName(value = "email") private String email;
        @SerializedName(value = "username") private String username;
        @SerializedName(value = "image") private String image;

        public String getEmail() { return email; }
        public String getUsername() { return username; }
        public String getImage() { return image; }
        public Long getMemberId() { return memberId; }
    }
}
