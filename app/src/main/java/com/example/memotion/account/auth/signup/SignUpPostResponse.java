package com.example.memotion.account.auth.signup;

import com.google.gson.annotations.SerializedName;

public class SignUpPostResponse {
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

        public Long getMemberId() {
            return memberId;
        }

        public String getEmail() {
            return email;
        }
    }
}
