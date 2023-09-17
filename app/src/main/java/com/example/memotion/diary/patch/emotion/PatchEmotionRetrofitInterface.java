package com.example.memotion.diary.patch.emotion;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PatchEmotionRetrofitInterface {
    @PATCH("diary/emotion/{diaryId}")
    Call<PatchEmotionResponse> patchEmotion(@Path("diaryId") Long diaryId, @Body PatchEmotionRequest request);
}
