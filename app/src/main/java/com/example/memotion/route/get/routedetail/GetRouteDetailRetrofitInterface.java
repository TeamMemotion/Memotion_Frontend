package com.example.memotion.route.get.routedetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetRouteDetailRetrofitInterface {
    @GET("route/route/list/{routeId}")
    Call<GetRouteDetailResponse> getRouteDetail(@Path(value = "routeId") Long id);
}
