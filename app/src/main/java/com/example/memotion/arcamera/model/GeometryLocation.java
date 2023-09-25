package com.example.memotion.arcamera.model;

import com.google.android.gms.maps.model.LatLng;

public class GeometryLocation {
    private double lat;
    private double lng;

    public GeometryLocation(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public LatLng getLatLng() {
        return new LatLng(lat, lng);
    }
}
