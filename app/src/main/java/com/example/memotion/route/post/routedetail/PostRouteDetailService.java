package com.example.memotion.route.post.routedetail;

import static com.example.memotion.RetrofitClient.errorParsing;
import static com.example.memotion.RetrofitClient.getClient;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.memotion.RetrofitClient;

import java.io.IOException;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostRouteDetailService {
    private PostRouteDetailResult postRouteDetailResult;

    public void setPostRouteDetailResult(PostRouteDetailResult postRouteDetailResult) {
        this.postRouteDetailResult = postRouteDetailResult;
    }

    public void postRouteDetail(MultipartBody.Part multipartFile, PostRouteDetailRequest postRouteDetailRequest) {
        PostRouteDetailRetrofitInterface postRouteDetailService = getClient().create(PostRouteDetailRetrofitInterface.class);
        postRouteDetailService.postRouteDetail(multipartFile, postRouteDetailRequest).enqueue(new Callback<PostRouteDetailResponse>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<PostRouteDetailResponse> call, Response<PostRouteDetailResponse> response) {
                Log.d("POST-ROUTE-DETAIL-SUCCESS", response.toString());

                if(response.isSuccessful()) {
                    if(response.body().getCode() == 1000) {
                        postRouteDetailResult.postRouteDetailSuccess(response.body().getCode(), response.body().getResult());
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
                            case 2002:
                                postRouteDetailResult.postRouteDetailFailure(errorResponse.getCode(), errorResponse.getMessage());
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<PostRouteDetailResponse> call, Throwable t) {
                Log.d("POST-ROUTE-DETAIL-FAILURE", t.getMessage());
            }
        });
    }
}
