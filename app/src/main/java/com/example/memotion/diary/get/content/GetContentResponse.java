package com.example.memotion.diary.get.content;

import com.google.gson.annotations.SerializedName;

public class GetContentResponse {
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

    static class Result {
        @SerializedName(value = "diaryContentId") private Long diaryContentId;
        @SerializedName(value = "createdDate") private String createdDate;
        @SerializedName(value = "title") private String title;
        @SerializedName(value = "content") private String content;
        @SerializedName(value = "title") private String keyWord;
        @SerializedName(value = "memberId") private Long memberId;

        public Long getDiaryContentId() {
            return diaryContentId;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }

        public String getKeyWord() {
            return keyWord;
        }

        public Long getMemberId() {
            return memberId;
        }
    }
}
