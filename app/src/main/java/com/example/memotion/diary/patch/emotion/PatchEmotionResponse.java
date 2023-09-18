package com.example.memotion.diary.patch.emotion;

import com.google.gson.annotations.SerializedName;

public class PatchEmotionResponse {
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

    public static class Result {
        @SerializedName(value = "diaryId") private Long diaryId;
        @SerializedName(value = "longitude") private Double longitude;
        @SerializedName(value = "latitude") private Double latitude;
        @SerializedName(value = "emotion") private String emotion;
        @SerializedName(value = "keyWord") private String keyWord;  // 감정분석 결과
        @SerializedName(value = "createdDate") private String createdDate;
        @SerializedName(value = "updatedDate") private String updatedDate;
        @SerializedName(value = "share") private boolean share;
        @SerializedName(value = "memberId") private Long memberId;

        public Long getDiaryId() {
            return diaryId;
        }

        public void setDiaryId(Long diaryId) {
            this.diaryId = diaryId;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public String getEmotion() {
            return emotion;
        }

        public void setEmotion(String emotion) {
            this.emotion = emotion;
        }

        public String getKeyWord() {
            return keyWord;
        }

        public void setKeyWord(String keyWord) {
            this.keyWord = keyWord;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getUpdatedDate() {
            return updatedDate;
        }

        public void setUpdatedDate(String updatedDate) {
            this.updatedDate = updatedDate;
        }

        public boolean isShare() {
            return share;
        }

        public void setShare(boolean share) {
            this.share = share;
        }

        public Long getMemberId() {
            return memberId;
        }

        public void setMemberId(Long memberId) {
            this.memberId = memberId;
        }
    }
}