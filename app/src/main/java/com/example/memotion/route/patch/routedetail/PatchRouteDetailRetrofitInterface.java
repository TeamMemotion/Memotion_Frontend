package com.example.memotion.route.patch.routedetail;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PatchRouteDetailRetrofitInterface {
    @POST("route/update/{routeDetailId}")
    Call<PatchRouteDetailResponse> patchRouteDetail(@Path("routeDetailId") Long routeDetailId, @Body PatchRouteDetailRequest request);
}
