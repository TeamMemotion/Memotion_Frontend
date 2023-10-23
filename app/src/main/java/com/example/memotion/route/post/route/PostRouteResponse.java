package com.example.memotion.route.post.route;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class PostRouteResponse {
    @SerializedName(value = "isSuccess")
    private Boolean isSuccess;

    @SerializedName(value = "code")
    private int code;

    @SerializedName(value = "message")
    private String message;

    @SerializedName(value = "result")
    private Long result;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Long getResult() {
        return result;
    }

}
