package com.example.memotion.home.get.emotions;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetEmotionsRetrofitInterface {
    @GET("diary/content/month/{date}")
    Call<GetEmotionsResponse> getEmotions(@Path(value = "date") String date);
}
