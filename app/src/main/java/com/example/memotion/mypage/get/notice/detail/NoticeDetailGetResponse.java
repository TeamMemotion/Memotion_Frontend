package com.example.memotion.mypage.get.notice.detail;

import com.example.memotion.mypage.get.notice.all.NoticeAllGetResponse;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NoticeDetailGetResponse {
    @SerializedName(value = "isSuccess") private Boolean isSuccess;
    @SerializedName(value = "code") private int code;
    @SerializedName(value = "message") private String message;
    @SerializedName(value = "result") private NoticeAllGetResponse.Result result;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public NoticeAllGetResponse.Result getResult() {
        return result;
    }
}
