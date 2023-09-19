package com.example.memotion.home.get.emotions;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetEmotionsResponse {
    @SerializedName(value = "isSuccess") private Boolean isSuccess;
    @SerializedName(value = "code") private int code;
    @SerializedName(value = "message") private String message;
    @SerializedName(value = "result") private ArrayList<GetEmotionsResponse.Result> result;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<Result> getResult() {
        return result;
    }

    public static class Result {
        @SerializedName(value = "keyword") private String keyWord;
        @SerializedName(value = "createdDate") private String createdDate;
        @SerializedName(value = "memberId") private Long memberId;

        public String getKeyWord() { return keyWord; }
        public String getCreatedDate() { return createdDate; }
        public Long getMemberId() { return memberId; }
    }
}
