package com.example.memotion.route.post.route;

import com.google.gson.annotations.SerializedName;

public class PostRouteRequest {
    @SerializedName(value = "name")
    private String name;

    @SerializedName(value = "startDate")
    private String startDate;

    @SerializedName(value = "endDate")
    private String endDate;

    public PostRouteRequest(String name, String startDate, String endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
