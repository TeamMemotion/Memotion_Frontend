package com.example.memotion.diary.get.emotion;

import com.google.gson.annotations.SerializedName;

public class GetEmotionResponse {
    @SerializedName(value = "isSuccess") private Boolean isSuccess;
    @SerializedName(value = "code") private int code;
    @SerializedName(value = "message") private String message;
    @SerializedName(value = "result") private GetEmotionResponse.Result result;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public GetEmotionResponse.Result getResult() {
        return result;
    }

    public static class Result {

    }
}
