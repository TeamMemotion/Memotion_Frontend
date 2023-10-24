package com.example.memotion.route.post.routedetail;

import com.google.gson.annotations.SerializedName;

public class PostRouteDetailResponse {
    @SerializedName(value = "isSuccess")
    private Boolean isSuccess;

    @SerializedName(value = "code")
    private int code;

    @SerializedName(value = "message")
    private String message;

    @SerializedName(value = "result")
    private PostRouteDetailResponse.Result result;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public PostRouteDetailResponse.Result getResult() {
        return result;
    }

    public static class Result {
        @SerializedName(value = "content")
        private String content;

        @SerializedName(value = "end_time")
        private String endTime;

        @SerializedName(value = "latitude")
        private Double latitude;

        @SerializedName(value = "longitude")
        private Double longitude;

        @SerializedName(value = "place")
        private String place;

        @SerializedName(value = "routeId")
        private Long routeId;

        @SerializedName(value = "select_date")
        private String selectDate;

        @SerializedName(value = "start_time")
        private String startTime;

        @SerializedName(value = "title")
        private String title;

        @SerializedName(value = "url")
        private String url;

        public String getContent() {
            return content;
        }

        public String getEndTime() {
            return endTime;
        }

        public Double getLatitude() {
            return latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public String getPlace() {
            return place;
        }

        public Long getRouteId() {
            return routeId;
        }

        public String getSelectDate() {
            return selectDate;
        }

        public String getStartTime() {
            return startTime;
        }

        public String getTitle() {
            return title;
        }

        public String getUrl() {
            return url;
        }
    }
}
