package com.example.memotion.arcamera.api;

import com.example.memotion.arcamera.model.Place;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class NearbyPlacesResponse {
    @SerializedName("results")
    private List<Place> results;

    public NearbyPlacesResponse(List<Place> results) {
        this.results = results;
    }

    public List<Place> getResults() {
        return results;
    }
}