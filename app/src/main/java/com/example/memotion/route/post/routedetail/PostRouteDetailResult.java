package com.example.memotion.route.post.routedetail;

public interface PostRouteDetailResult {
    void postRouteDetailSuccess(int code, PostRouteDetailResponse.Result result);
    void postRouteDetailFailure(int code, String message);
}
