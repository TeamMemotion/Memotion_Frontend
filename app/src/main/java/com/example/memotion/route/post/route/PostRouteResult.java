package com.example.memotion.route.post.route;

public interface PostRouteResult {
    void postRouteSuccess(int code, Long result);
    void postRouteFailure(int code, String message);
}
