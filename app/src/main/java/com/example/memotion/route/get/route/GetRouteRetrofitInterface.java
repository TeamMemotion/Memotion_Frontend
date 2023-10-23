package com.example.memotion.route.get.route;

import com.example.memotion.route.get.routedetail.GetRouteDetailResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetRouteRetrofitInterface {
    @GET("route/route-detail/{routeId}")
    Call<GetRouteResponse> getRoute(@Path(value = "routeId") Long routeId);
}
