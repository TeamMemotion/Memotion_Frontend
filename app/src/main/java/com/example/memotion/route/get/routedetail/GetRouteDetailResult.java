package com.example.memotion.route.get.routedetail;

import java.util.ArrayList;

public interface GetRouteDetailResult {
    void getRouteDetailSuccess(int code, ArrayList<GetRouteDetailResponse.Result> result);
    void getRouteDetailFailure(int code, String message);
}
