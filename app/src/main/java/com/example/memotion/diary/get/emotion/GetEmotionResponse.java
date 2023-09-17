package com.example.memotion.diary.get.emotion;

import com.example.memotion.diary.get.content.GetContentResponse;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class GetEmotionResponse {
    @SerializedName(value = "isSuccess") private Boolean isSuccess;
    @SerializedName(value = "code") private int code;
    @SerializedName(value = "message") private String message;
    @SerializedName(value = "result") private ArrayList<Result> result;

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
        @SerializedName(value = "diaryId") private Long diaryId;
        @SerializedName(value = "longitude") private Double longitude;
        @SerializedName(value = "latitude") private Double latitude;
        @SerializedName(value = "emotion") private String emotion;
        @SerializedName(value = "keyWord") private String keyWord;
        @SerializedName(value = "createdDate") private String createdDate;
        @SerializedName(value = "updatedDate") private String updatedDate;
        @SerializedName(value = "share") private boolean share;
        @SerializedName(value = "memberId") private Long memberId;

        public Long getDiaryId() {
            return diaryId;
        }

        public Double getLongitude() {
            return longitude;
        }

        public Double getLatitude() {
            return latitude;
        }

        public String getEmotion() {
            return emotion;
        }

        public String getKeyWord() {
            return keyWord;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public String getUpdatedDate() {
            return updatedDate;
        }

        public boolean isShare() {
            return share;
        }

        public Long getMemberId() {
            return memberId;
        }
    }
}
