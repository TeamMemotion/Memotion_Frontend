package com.example.memotion.route.get.routedetailList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetRouteDetailListRetrofitInterface {
    @GET("route/route/list/{routeId}")
    Call<GetRouteDetailListResponse> getRouteDetailList(@Path(value = "routeId") Long id);
}
