package com.example.memotion.route.get.route.myRoute;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MyRouteGetResponse {
    @SerializedName(value = "isSuccess") private Boolean isSuccess;
    @SerializedName(value = "code") private int code;
    @SerializedName(value = "message") private String message;
    @SerializedName(value = "result") private ArrayList<MyRouteGetResponse.Result> result;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<MyRouteGetResponse.Result> getResult() {
        return result;
    }

    public static class Result {
        @SerializedName(value = "routeId") private Long routeId;
        @SerializedName(value = "routeImg") private String routeImg;
        @SerializedName(value = "startDate") private String startDate;
        @SerializedName(value = "endDate") private String endDate;
        @SerializedName(value = "name") private String name;
        @SerializedName(value = "content") private String content;
        @SerializedName(value = "profileImg") private String profileImg;
        @SerializedName(value = "username") private String username;
        @SerializedName(value = "likeCount") private Long likeCount;
        @SerializedName(value = "liked") private boolean liked;

        public Long getRouteId() {
            return routeId;
        }

        public String getRouteImg() {
            return routeImg;
        }

        public String getStartDate() {
            return startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public String getName() {
            return name;
        }

        public String getContent() {
            return content;
        }

        public String getProfileImg() {
            return profileImg;
        }

        public String getUsername() {
            return username;
        }

        public Long getLikeCount() {
            return likeCount;
        }

        public boolean isLiked() {
            return liked;
        }
    }
}
