package com.example.memotion.local.get.search;

import com.example.memotion.route.get.localGuide.latest.LocalGuideGetResponse;

import java.util.ArrayList;

public interface SearchRouteGetResult {
    void getSearchRouteSuccess(int code, ArrayList<LocalGuideGetResponse.Result> result);
    void getSearchRouteFailure(int code, String message);
}
