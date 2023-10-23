package com.example.memotion.route.get.routedetail;

import com.example.memotion.diary.get.emotion.GetEmotionResponse;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class GetRouteDetailResponse {
    @SerializedName(value = "isSuccess") private Boolean isSuccess;
    @SerializedName(value = "code") private int code;
    @SerializedName(value = "message") private String message;
    @SerializedName(value = "result") private ArrayList<GetRouteDetailResponse.Result> result;

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
        @SerializedName(value ="content") private String content;
        @SerializedName(value ="end_time") private LocalDateTime end_time;
        @SerializedName(value ="latitude") private Double latitude;
        @SerializedName(value ="longitude") private Double longitude;
        @SerializedName(value ="place") private String place;
        @SerializedName(value = "routeId") private Long routeId;
        @SerializedName(value ="select_date") private LocalDate select_date;
        @SerializedName(value ="start_time") private LocalDateTime start_time;
        @SerializedName(value = "title") private String title;
        @SerializedName(value = "url" ) private String url;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public LocalDateTime getEnd_time() {
            return end_time;
        }

        public void setEnd_time(LocalDateTime end_time) {
            this.end_time = end_time;
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

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public Long getRouteId() {
            return routeId;
        }

        public void setRouteId(Long routeId) {
            this.routeId = routeId;
        }

        public LocalDate getSelect_date() {
            return select_date;
        }

        public void setSelect_date(LocalDate select_date) {
            this.select_date = select_date;
        }

        public LocalDateTime getStart_time() {
            return start_time;
        }

        public void setStart_time(LocalDateTime start_time) {
            this.start_time = start_time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
