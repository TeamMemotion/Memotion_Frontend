package com.example.memotion.route.post.route;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PostRouteRetrofitInterface {
    @POST("route")
    Call<PostRouteResponse> postRoute(@Body PostRouteRequest request);
}
