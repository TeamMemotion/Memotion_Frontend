package com.example.memotion.diary.patch.emotion;

import static com.example.memotion.RetrofitClient.errorParsing;
import static com.example.memotion.RetrofitClient.getClient;

import android.util.Log;

import com.example.memotion.RetrofitClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatchEmotionService {
    private PatchEmotionResult patchEmotionResult;

    public void setPatchEmotionResult(PatchEmotionResult patchEmotionResult) {
        this.patchEmotionResult = patchEmotionResult;
    }

    public void postEmotion(PatchEmotionRequest patchEmotionRequest) {
        PatchEmotionRetrofitInterface patchEmotionService = getClient().create(PatchEmotionRetrofitInterface.class);
        patchEmotionService.patchEmotion(patchEmotionRequest).enqueue(new Callback<PatchEmotionResponse>() {
            @Override
            public void onResponse(Call<PatchEmotionResponse> call, Response<PatchEmotionResponse> response) {
                Log.d("PATCH-EMOTION-SUCCESS", response.toString());

                if(response.isSuccessful()) {
                    if(response.body().getCode() == 1000) {
                        patchEmotionResult.patchEmotionSuccess(response.body().getCode(), response.body().getResult());
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
            public void onFailure(Call<PatchEmotionResponse> call, Throwable t) {
                Log.d("PATCH-EMOTION-FAILURE", t.getMessage());
            }
        });
    }
}
