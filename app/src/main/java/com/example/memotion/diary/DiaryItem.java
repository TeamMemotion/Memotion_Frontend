package com.example.memotion.diary;

public class DiaryItem {
    Long diaryId;
    Double latitude;
    Double longitude;
    String emotion;
    String keyword;

    public DiaryItem(Long diaryId, Double latitude, Double longitude, String emotion, String keyword) {
        this.diaryId = diaryId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.emotion = emotion;
        this.keyword = keyword;
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
