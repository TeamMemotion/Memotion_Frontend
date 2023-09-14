package com.example.memotion.diary.get.content;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetContentRetrofitInterface {
    @GET("diary/content/{date}")
    Call<GetContentResponse> getContent(@Path(value="date") String date);
}
