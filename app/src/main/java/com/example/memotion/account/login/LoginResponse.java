package com.example.memotion.account.login;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("isSuccess")
    private Boolean isSuccess;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("result")
    private Result result;

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public Result getResult() {
        return this.result;
    }

    static class Result {
        @SerializedName("access_token")
        private String accessToken;

        @SerializedName("refresh_token")
        private String refreshToken;
    }
}
