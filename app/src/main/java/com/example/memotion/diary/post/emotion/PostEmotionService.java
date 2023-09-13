package com.example.memotion.diary.post.emotion;

import static com.example.memotion.RetrofitClient.errorParsing;
import static com.example.memotion.RetrofitClient.getClient;

import android.util.Log;

import com.example.memotion.RetrofitClient;
import com.example.memotion.account.login.LoginRetrofitInterface;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostEmotionService {
    private PostEmotionResult postEmotionResult;

    public void setPostEmotionResult(PostEmotionResult postEmotionResult) {
        this.postEmotionResult = postEmotionResult;
    }

    public void postEmotion(PostEmotionRequest postEmotionRequest) {
        PostEmotionRetrofitInterface postEmotionService = getClient().create(PostEmotionRetrofitInterface.class);
        postEmotionService.postEmotion(postEmotionRequest).enqueue(new Callback<PostEmotionResponse>() {
            @Override
            public void onResponse(Call<PostEmotionResponse> call, Response<PostEmotionResponse> response) {
                Log.d("POST-EMOTION-SUCCESS", response.toString());

                if(response.isSuccessful()) {
                    if(response.body().getCode() == 1000) {
                        postEmotionResult.postEmotionSuccess(response.body().getCode(), response.body().getResult());
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
            public void onFailure(Call<PostEmotionResponse> call, Throwable t) {
                Log.d("POST-EMOTION-FAILURE", t.getMessage());
            }
        });
    }
}
