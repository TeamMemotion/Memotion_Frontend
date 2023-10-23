package com.example.memotion.route.get.route;

public interface GetRouteResult {
    void getRouteSuccess(int code, GetRouteResponse.Result result);
    void getRouteFailure(int code, String message);
}
