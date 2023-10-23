package com.example.memotion.route.get.localGuide.latest;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LocalGuideGetRetrofitInterface {
    @GET("route/localGuide")
    Call<LocalGuideGetResponse> getLocalGuide();
}
