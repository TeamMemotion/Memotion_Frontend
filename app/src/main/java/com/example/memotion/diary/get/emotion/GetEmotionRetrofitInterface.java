package com.example.memotion.diary.get.emotion;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetEmotionRetrofitInterface {
    @GET("diary/{date}")
    Call<GetEmotionResponse> getEmotion(@Path(value="date") String date);
}
