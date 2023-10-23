package com.example.memotion.route.post.route;

import static com.example.memotion.RetrofitClient.errorParsing;
import static com.example.memotion.RetrofitClient.getClient;

import android.util.Log;

import com.example.memotion.RetrofitClient;
import com.example.memotion.diary.post.emotion.PostEmotionRequest;
import com.example.memotion.diary.post.emotion.PostEmotionResponse;
import com.example.memotion.diary.post.emotion.PostEmotionResult;
import com.example.memotion.diary.post.emotion.PostEmotionRetrofitInterface;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostRouteService {
    private PostRouteResult postRouteResult;

    public void setPostRouteResult(PostRouteResult postRouteResult) {
        this.postRouteResult = postRouteResult;
    }

    public void postRoute(PostRouteRequest postRouteRequest) {
        PostRouteRetrofitInterface postRouteService = getClient().create(PostRouteRetrofitInterface.class);
        postRouteService.postRoute(postRouteRequest).enqueue(new Callback<PostRouteResponse>() {
            @Override
            public void onResponse(Call<PostRouteResponse> call, Response<PostRouteResponse> response) {
                Log.d("POST-ROUTE-SUCCESS", response.toString());

                if(response.isSuccessful()) {
                    if(response.body().getCode() == 1000) {
                        postRouteResult.postRouteSuccess(response.body().getCode(), response.body().getResult());
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
            public void onFailure(Call<PostRouteResponse> call, Throwable t) {
                Log.d("POST-ROUTE-FAILURE", t.getMessage());
            }
        });
    }
}
