package com.example.memotion.search.get;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchGetResponse {
    @SerializedName(value = "isSuccess") private Boolean isSuccess;
    @SerializedName(value = "code") private int code;
    @SerializedName(value = "message") private String message;
    @SerializedName(value = "result") private ArrayList<SearchGetResponse.Result> result;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<SearchGetResponse.Result> getResult() {
        return result;
    }

    public static class Result {
        @SerializedName(value = "diaryId") private Long diaryId;
        @SerializedName(value = "keyWord") private String keyWord;
        @SerializedName(value = "latitude") private Double latitude;
        @SerializedName(value = "longitude") private Double longitude;
        @SerializedName(value = "place") private String place;
        @SerializedName(value = "share") private boolean share;
        @SerializedName(value = "emotion") private String emotion;
        @SerializedName(value = "createdDate") private String createdDate;

        public Long getDiaryId() {
            return diaryId;
        }

        public String getKeyWord() {
            return keyWord;
        }

        public Double getLatitude() {
            return latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public String getEmotion() {
            return emotion;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public String getPlace() {
            return place;
        }

        public boolean isShare() {
            return share;
        }
    }
}
