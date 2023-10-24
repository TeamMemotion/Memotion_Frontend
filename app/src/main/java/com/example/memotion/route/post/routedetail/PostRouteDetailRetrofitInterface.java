package com.example.memotion.route.post.routedetail;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PostRouteDetailRetrofitInterface {
    @POST("route/save")
    Call<PostRouteDetailResponse> postRouteDetail(@Body PostRouteDetailRequest request);
}
