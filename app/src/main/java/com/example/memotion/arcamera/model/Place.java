package com.example.memotion.arcamera.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.ar.sceneform.math.Vector3;

public class Place {
    private String id;
    private String icon;
    private String name;
    private Geometry geometry;

    public Place(String id, String icon, String name, Geometry geometry) {
        this.id = id;
        this.icon = icon;
        this.name = name;
        this.geometry = geometry;
    }

    public String getName() {
        return name;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Place place = (Place) obj;
        return id.equals(place.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public Vector3 getPositionVector(float azimuth, LatLng latLng) {
        LatLng placeLatLng = this.geometry.getLocation().getLatLng();
        // TODO: compute heading
        double heading = 0.0;
        float r = -2f;
        float x = (float) (r * Math.sin(azimuth + heading));
        float y = 1f;
        float z = (float) (r * Math.cos(azimuth + heading));
        return new Vector3(x, y, z);
    }
}
