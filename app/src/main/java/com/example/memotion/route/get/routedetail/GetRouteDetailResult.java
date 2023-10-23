package com.example.memotion.route.get.routedetail;

import java.util.ArrayList;

public interface GetRouteDetailResult {

    void getRouteDeatilSuccess(int code, ArrayList<GetRouteDetailResponse.Result> result);
    void getRouteDeatilFailure(int code, String message);
}
