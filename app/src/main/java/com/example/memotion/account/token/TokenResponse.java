package com.example.memotion.account.token;

import com.google.gson.annotations.SerializedName;

public class TokenResponse {
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

    // 최상위 패키지에서 참조하기 때문에 public으로 만들었다.
    public static class Result {
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
