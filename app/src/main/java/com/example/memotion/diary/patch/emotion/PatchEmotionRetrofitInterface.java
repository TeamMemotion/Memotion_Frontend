package com.example.memotion.diary.patch.emotion;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PatchEmotionRetrofitInterface {
    @POST("diary/emotion")
    Call<PatchEmotionResponse> patchEmotion(@Body PatchEmotionRequest request);
}
