package com.example.memotion.local.get.popular;

import com.example.memotion.route.get.localGuide.latest.LocalGuideGetResponse;

import java.util.ArrayList;

public interface PopularRouteGetResult {
    void getPopularRouteSuccess(int code, ArrayList<LocalGuideGetResponse.Result> result);
    void getPopularRouteFailure(int code, String message);
}
