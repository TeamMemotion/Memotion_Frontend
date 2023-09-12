package com.example.memotion.diary.post.emotion;

import com.example.memotion.account.login.LoginRequest;
import com.example.memotion.account.login.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PostEmotionRetrofitInterface {
    @POST("diary/emotion")
    Call<PostEmotionResponse> postEmotion(@Body PostEmotionRequest request);
}
