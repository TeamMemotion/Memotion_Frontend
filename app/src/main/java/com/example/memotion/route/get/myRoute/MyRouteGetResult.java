package com.example.memotion.route.get.route.myRoute;

import java.util.ArrayList;

public interface MyRouteGetResult {
    void getMyRouteSuccess(int code, ArrayList<MyRouteGetResponse.Result> result);
    void getMyRouteFailure(int code, String message);
}
