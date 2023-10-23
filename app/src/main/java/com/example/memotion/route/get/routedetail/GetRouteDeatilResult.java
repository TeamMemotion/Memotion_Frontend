package com.example.memotion.route.get.routedetail;

import com.example.memotion.diary.get.content.GetContentResponse;

import java.util.ArrayList;

public interface GetRouteDeatilResult {

    void getRouteDeatilSuccess(int code, ArrayList<GetRouteDetailResponse.Result> result);
    void getRouteDeatilFailure(int code, String message);
}
