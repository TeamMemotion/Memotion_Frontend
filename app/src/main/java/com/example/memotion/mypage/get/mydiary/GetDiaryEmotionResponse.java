package com.example.memotion.mypage.get.mydiary;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetDiaryEmotionResponse {
    @SerializedName(value = "isSuccess") private Boolean isSuccess;
    @SerializedName(value = "code") private int code;
    @SerializedName(value = "message") private String message;
    @SerializedName(value = "result") private ArrayList<GetDiaryEmotionResponse.Result> result;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<GetDiaryEmotionResponse.Result> getResult() {
        return result;
    }

    public static class Result {
        @SerializedName(value = "diaryContentId") private Long diaryContentId;
        @SerializedName(value = "createdDate") private String createdDate;
        @SerializedName(value = "title") private String title;
        @SerializedName(value = "content") private String content;
        @SerializedName(value = "keyWord") private String keyWord;
        @SerializedName(value = "memberId") private Long memberId;


        public Long getDiaryContentId() { return diaryContentId; }
        public String getTitle() { return title; }
        public String getContent() { return content; }
        public String getKeyWord() { return keyWord; }
        public String getCreatedDate() { return createdDate; }
        public Long getMemberId() { return memberId; }
    }
}
