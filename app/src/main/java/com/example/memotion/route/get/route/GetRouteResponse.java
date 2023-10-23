package com.example.memotion.route.get.route;

import com.google.gson.annotations.SerializedName;

public class GetRouteResponse {
    @SerializedName(value = "isSuccess")
    private Boolean isSuccess;
    @SerializedName(value = "code")
    private int code;
    @SerializedName(value = "message")
    private String message;
    @SerializedName(value = "result")
    private GetRouteResponse.Result result;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public GetRouteResponse.Result getResult() {
        return result;
    }

    public static class Result {
        @SerializedName(value = "content")
        private String content;
        @SerializedName(value = "endDate")
        private String endDate;
        @SerializedName(value = "likeCount")
        private Long likeCount;
        @SerializedName(value = "liked")
        private boolean liked;
        @SerializedName(value = "name")
        private String name;
        @SerializedName(value = "profileImg")
        private String profileImg;
        @SerializedName(value = "routeId")
        private Long routeId;
        @SerializedName(value = "routeImg")
        private String routeImg;
        @SerializedName(value = "startDate")
        private String startDate;
        @SerializedName(value = "username")
        private String username;

        public String getContent() {
            return content;
        }

        public String getEndDate() {
            return endDate;
        }

        public Long getLikeCount() {
            return likeCount;
        }

        public boolean isLiked() {
            return liked;
        }

        public String getName() {
            return name;
        }

        public String getProfileImg() {
            return profileImg;
        }

        public Long getRouteId() {
            return routeId;
        }

        public String getRouteImg() {
            return routeImg;
        }

        public String getStartDate() {
            return startDate;
        }

        public String getUsername() {
            return username;
        }
    }
}
