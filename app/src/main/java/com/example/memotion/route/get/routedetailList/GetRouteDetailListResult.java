package com.example.memotion.route.get.routedetailList;

import java.util.ArrayList;

public interface GetRouteDetailListResult {

    void getRouteDetailListSuccess(int code, ArrayList<GetRouteDetailListResponse.Result> result);
    void getRouteDetailListFailure(int code, String message);
}
