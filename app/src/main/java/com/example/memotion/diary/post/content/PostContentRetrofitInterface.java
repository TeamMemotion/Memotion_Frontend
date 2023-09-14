package com.example.memotion.diary.post.content;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PostContentRetrofitInterface {
    @POST("diary/content")
    Call<PostContentResponse> postContent(@Body PostContentRequest request);
}
