package com.example.memotion.local.get.popular;

import com.example.memotion.route.get.localGuide.latest.LocalGuideGetResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PopularRouteGetRetrofitInterface {
    @GET("route/localGuide/popular")
    Call<LocalGuideGetResponse> getPopularRoute(@Query("latitude") Double latitude, @Query("longitude") Double longitude);
}
