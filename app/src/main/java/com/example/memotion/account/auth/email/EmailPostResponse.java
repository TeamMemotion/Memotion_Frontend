package com.example.memotion.account.auth.email;

import com.google.gson.annotations.SerializedName;

public class EmailPostResponse {
    @SerializedName(value = "isSuccess") private Boolean isSuccess;
    @SerializedName(value = "code") private int code;
    @SerializedName(value = "message") private String message;
    @SerializedName(value = "result") private String result;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getResult() {
        return result;
    }
}
