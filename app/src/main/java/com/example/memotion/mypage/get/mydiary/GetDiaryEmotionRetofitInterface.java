package com.example.memotion.mypage.get.mydiary;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetDiaryEmotionRetofitInterface {
    @GET("diary/list/{emotion}")
    Call<GetDiaryEmotionResponse> getDiaryEmotion(@Path(value = "emotion") String emotion);
}
