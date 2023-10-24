package com.example.memotion.route;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class RouteDetailItem implements Serializable {
    private String title;

    private String start_time;

    private String end_time;

    private String select_date;

    private String content;

    private String place;

    private Double latitude;

    private Double longitude;

    private String url;

    private Long routeId;

    private Long recordDetailId;

    public RouteDetailItem(String title, String start_time, String end_time, String select_date, String content, String place, Double latitude, Double longitude, String url, Long routeId , Long recordDetailId) {
        this.title = title;
        this.start_time = start_time;
        this.end_time = end_time;
        this.select_date = select_date;
        this.content = content;
        this.place = place;
        this.latitude = latitude;
        this.longitude = longitude;
        this.url = url;
        this.routeId = routeId;
        this.recordDetailId = recordDetailId;
    }

    public Long getRecordDetailId() {
        return recordDetailId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getSelect_date() {
        return select_date;
    }

    public void setSelect_date(String select_date) {
        this.select_date = select_date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }
}
