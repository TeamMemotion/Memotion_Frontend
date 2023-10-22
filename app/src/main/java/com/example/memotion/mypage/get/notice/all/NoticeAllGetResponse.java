package com.example.memotion.mypage.get.notice.all;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.util.ArrayList;

public class NoticeAllGetResponse {
    @SerializedName(value = "isSuccess") private Boolean isSuccess;
    @SerializedName(value = "code") private int code;
    @SerializedName(value = "message") private String message;
    @SerializedName(value = "result") private ArrayList<NoticeAllGetResponse.Result> result;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<NoticeAllGetResponse.Result> getResult() {
        return result;
    }

    public static class Result {
        @SerializedName(value = "noticeId") private Long noticeId;
        @SerializedName(value = "name") private String name;
        @SerializedName(value = "content") private String content;
        @SerializedName(value = "createdAt") private String createdAt;

        public Long getNoticeId() {
            return noticeId;
        }

        public String getName() {
            return name;
        }

        public String getContent() {
            return content;
        }

        public String getCreatedAt() {
            return createdAt;
        }
    }
}
