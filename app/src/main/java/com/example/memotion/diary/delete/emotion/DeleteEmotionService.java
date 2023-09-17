package com.example.memotion.diary.delete.emotion;

import static com.example.memotion.RetrofitClient.errorParsing;
import static com.example.memotion.RetrofitClient.getClient;

import android.util.Log;

import com.example.memotion.RetrofitClient;
import com.example.memotion.diary.patch.emotion.PatchEmotionRequest;
import com.example.memotion.diary.patch.emotion.PatchEmotionResponse;
import com.example.memotion.diary.patch.emotion.PatchEmotionResult;
import com.example.memotion.diary.patch.emotion.PatchEmotionRetrofitInterface;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteEmotionService {
    private DeleteEmotionResult deleteEmotionResult;

    public void setDeleteEmotionResult(DeleteEmotionResult deleteEmotionResult) {
        this.deleteEmotionResult = deleteEmotionResult;
    }

    public void deleteEmotion(Long diaryId) {
        DeleteEmotionRetrofitInterface deleteEmotionService = getClient().create(DeleteEmotionRetrofitInterface.class);
        deleteEmotionService.deleteEmotion(diaryId).enqueue(new Callback<DeleteEmotionResponse>() {
            @Override
            public void onResponse(Call<DeleteEmotionResponse> call, Response<DeleteEmotionResponse> response) {
                Log.d("DELETE-EMOTION-SUCCESS", response.toString());

                if(response.isSuccessful()) {
                    if(response.body().getCode() == 1000) {
                        deleteEmotionResult.deleteEmotionSuccess(response.body().getCode(), response.body().getResult());
                    }
                } else {
                    //400이상 에러시 response.body가 null로 처리됨. 따라서 errorBody로 받아야함.
                    try {
                        String errorBody = response.errorBody().string();
                        RetrofitClient.ErrorResponse errorResponse = errorParsing(errorBody);

                        Log.d("ErrorBody : ", errorBody);
                        Log.d("errorCode: ", String.valueOf(errorResponse.getCode()));
                        Log.d("errorMessage: ", errorResponse.getMessage());

                        switch (errorResponse.getCode()) {
                            case 500:
                            case 2001:
                                // 어진이한테 에러처리 물어보고 추가하기
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<DeleteEmotionResponse> call, Throwable t) {
                Log.d("DELETE-EMOTION-FAILURE", t.getMessage());
            }
        });
    }
}
