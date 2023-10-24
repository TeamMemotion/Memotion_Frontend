package com.example.memotion.route.get.myRoute;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyRouteGetRetrofitInterface {
    @GET("route")
    Call<MyRouteGetResponse> getMyRoute();
}
