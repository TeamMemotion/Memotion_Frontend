package com.example.memotion.route.post.routedetail;

import com.google.gson.annotations.SerializedName;

public class PostRouteDetailRequest {
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

    public PostRouteDetailRequest(String content, String endTime, Double latitude, Double longitude, String place, Long routeId, String selectDate, String startTime, String title, String url) {
        this.content = content;
        this.endTime = endTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.place = place;
        this.routeId = routeId;
        this.selectDate = selectDate;
        this.startTime = startTime;
        this.title = title;
        this.url = url;
    }
}
