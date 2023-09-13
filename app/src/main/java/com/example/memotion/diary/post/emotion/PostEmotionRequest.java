package com.example.memotion.diary.post.emotion;

import com.google.gson.annotations.SerializedName;

public class PostEmotionRequest {
    @SerializedName(value = "latitude")
    private Double latitude;
    @SerializedName(value = "longitude")
    private Double longitude;
    @SerializedName(value = "emotion")
    private String emotion;
    @SerializedName(value = "keyWord")
    private String keyWord;
    @SerializedName(value = "createdDate")
    private String createdDate;
    @SerializedName(value = "share")
    private boolean share;

    public PostEmotionRequest(Double latitude, Double longitude, String emotion, String keyWord, String createdDate, boolean share) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.emotion = emotion;
        this.keyWord = keyWord;
        this.createdDate = createdDate;
        this.share = share;
    }
}