package com.example.memotion.diary.patch.emotion;

import com.google.gson.annotations.SerializedName;

public class PatchEmotionRequest {
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

    public PatchEmotionRequest(Double latitude, Double longitude, String emotion, String keyWord, String createdDate, boolean share) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.emotion = emotion;
        this.keyWord = keyWord;
        this.createdDate = createdDate;
        this.share = share;
    }
}