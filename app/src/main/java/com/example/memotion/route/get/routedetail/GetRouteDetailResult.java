package com.example.memotion.route.get.routedetail;

public interface GetRouteDetailResult {
    void getRouteDetailSuccess(int code, GetRouteDetailResponse.Result result);
    void getRouteDetailFailure(int code, String message);
}
