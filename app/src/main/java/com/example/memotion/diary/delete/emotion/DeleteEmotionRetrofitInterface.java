package com.example.memotion.diary.delete.emotion;


import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DeleteEmotionRetrofitInterface {
    @DELETE("diary/emotion/{diaryId}")
    Call<DeleteEmotionResponse> deleteEmotion(@Path("diaryId") Long diaryId);
}
