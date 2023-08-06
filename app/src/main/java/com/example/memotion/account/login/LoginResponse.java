package com.example.memotion.account.login;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName(value = "isSuccess") private Boolean isSuccess;

    @SerializedName(value = "code") private int code;

    @SerializedName(value = "message") private String message;

    @SerializedName(value = "result") private Result result;

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
        @SerializedName(value = "accessToken") private String accessToken;
        @SerializedName(value = "refreshToken") private String refreshToken;

        public String getAccessToken() {
            return accessToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }
    }
}
