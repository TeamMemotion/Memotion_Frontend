package com.example.memotion.route.get.routedetail;

import com.example.memotion.route.get.routedetailList.GetRouteDetailListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetRouteDetailRetrofitInterface {
    @GET("/route/route/list/{routeId}")
    Call<GetRouteDetailResponse> getRouteDetail(@Path(value = "detailId") Long id);
}
