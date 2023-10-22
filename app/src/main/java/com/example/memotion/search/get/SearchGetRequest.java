package com.example.memotion.search.get;

import com.google.gson.annotations.SerializedName;

public class SearchGetRequest {
    @SerializedName(value = "latitude") private Double latitude;
    @SerializedName(value = "longitude") private Double longitude;
    @SerializedName(value = "filter") private String filter;

    public SearchGetRequest(Double latitude, Double longitude, String filter) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.filter = filter;
    }
}
