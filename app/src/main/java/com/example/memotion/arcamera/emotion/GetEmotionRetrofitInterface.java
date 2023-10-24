package com.example.memotion.arcamera.emotion;

//import retrofit2.Call;
//import retrofit2.http.GET;
//import retrofit2.http.Path;
//
//public interface GetEmotionRetrofitInterface {
//    @GET("diary/{date}")
//    Call<GetEmotionResponse> getEmotion(@Path(value="date") String date);
//}

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetEmotionRetrofitInterface {
    @GET("diary")
    Call<GetEmotionResponse> getEmotion(@Query("latitude") double latitude, @Query("longitude") double longitude);
}
