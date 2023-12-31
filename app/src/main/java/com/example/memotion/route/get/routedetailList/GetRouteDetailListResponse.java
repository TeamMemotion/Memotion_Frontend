package com.example.memotion.route.get.routedetailList;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetRouteDetailListResponse {
    @SerializedName(value = "isSuccess") private Boolean isSuccess;
    @SerializedName(value = "code") private int code;
    @SerializedName(value = "message") private String message;
    @SerializedName(value = "result") private GetRouteDetailListResponse.Result result;

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
        @SerializedName(value = "loginMemberId") private Long loginMemberId;
        @SerializedName(value = "routeMemberId") private Long routeMemberId;
        @SerializedName(value = "routeDetail") private ArrayList<RouteDetailResult> routeDetailResult;

        public Long getLoginMemberId() {
            return loginMemberId;
        }

        public Long getRouteMemberId() {
            return routeMemberId;
        }

        public ArrayList<RouteDetailResult> getRouteDetailResult() {
            return routeDetailResult;
        }

        public static class RouteDetailResult {
            @SerializedName(value ="content") private String content;
            @SerializedName(value ="end_time") private String end_time;
            @SerializedName(value ="latitude") private Double latitude;
            @SerializedName(value ="longitude") private Double longitude;
            @SerializedName(value = "memberId") private Long memberId;
            @SerializedName(value ="place") private String place;
            @SerializedName(value = "recordDetailId" ) private Long recordDetailId;
            @SerializedName(value = "routeId") private Long routeId;
            @SerializedName(value ="select_date") private String select_date;
            @SerializedName(value ="start_time") private String start_time;
            @SerializedName(value = "title") private String title;
            @SerializedName(value = "url" ) private String url;

            public Long getRecordDetailId() {
                return recordDetailId;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
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

            public String getSelect_date() {
                return select_date;
            }

            public void setSelect_date(String select_date) {
                this.select_date = select_date;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
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

            public Long getMemberId() {
                return memberId;
            }
        }
    }
}
