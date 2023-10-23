package com.example.memotion.local.get.search;

import com.example.memotion.route.get.localGuide.latest.LocalGuideGetResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchRouteGetRetrofitInterface {
    @GET("route/localGuide/search")
    Call<LocalGuideGetResponse> getSearchRoute(@Query("latitude") Double latitude, @Query("longitude") Double longitude);
}
