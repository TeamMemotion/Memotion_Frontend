package com.example.memotion.diary;

import java.io.Serializable;

public class DiaryItem implements Serializable {
    Long diaryId;
    Double latitude;
    Double longitude;
    String emotion;
    String keyword;
    String place;

    public DiaryItem(Long diaryId, Double latitude, Double longitude, String emotion, String keyword, String place) {
        this.diaryId = diaryId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.emotion = emotion;
        this.keyword = keyword;
        this.place = place;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Long getDiaryId() {
        return diaryId;
    }

    public void setDiaryId(Long diaryId) {
        this.diaryId = diaryId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
