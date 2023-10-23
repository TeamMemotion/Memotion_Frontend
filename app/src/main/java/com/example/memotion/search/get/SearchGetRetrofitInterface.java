package com.example.memotion.search.get;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SearchGetRetrofitInterface {
    @GET("search/{filter}")
    Call<SearchGetResponse> getSearch(@Path("filter") String filter, @Query("latitude") Double latitude, @Query("longitude") Double longitude);
}
