package com.example.memotion.local.get.popular;

import static com.example.memotion.RetrofitClient.errorParsing;
import static com.example.memotion.RetrofitClient.getClient;

import android.util.Log;

import com.example.memotion.RetrofitClient;
import com.example.memotion.route.get.localGuide.latest.LocalGuideGetResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularRouteGetService {

    private PopularRouteGetResult popularRouteGetResult;

    public void setPopularRouteGetResult(PopularRouteGetResult popularRouteGetResult) {
        this.popularRouteGetResult = popularRouteGetResult;
    }

    public void getPopularRoute(Double latitude, Double longitude) {
        PopularRouteGetRetrofitInterface getPopularRouteService = getClient().create(PopularRouteGetRetrofitInterface.class);

        getPopularRouteService.getPopularRoute(latitude, longitude).enqueue(new Callback<LocalGuideGetResponse>() {
            @Override
            public void onResponse(Call<LocalGuideGetResponse> call, Response<LocalGuideGetResponse> response) {
                Log.d("GET-POPULAR-SUCCESS", response.toString());

                if(response.isSuccessful()) {
                    if(response.body().getCode() == 1000) {
                        popularRouteGetResult.getPopularRouteSuccess(response.body().getCode(), response.body().getResult());
                    }
                } else {
                    //400이상 에러시 response.body가 null로 처리됨. 따라서 errorBody로 받아야함.
                    try {
                        String errorBody = response.errorBody().string();
                        RetrofitClient.ErrorResponse errorResponse = errorParsing(errorBody);

                        Log.d("ErrorBody : ", errorBody);
                        Log.d("errorCode: ", String.valueOf(errorResponse.getCode()));
                        Log.d("errorMessage: ", errorResponse.getMessage() != null && !errorResponse.getMessage().isEmpty() ? errorResponse.getMessage() : " ");

                        switch (errorResponse.getCode()) {
                            case 500:
                            case 2016:
                                popularRouteGetResult.getPopularRouteFailure(errorResponse.getCode(), errorResponse.getMessage());
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<LocalGuideGetResponse> call, Throwable t) {
                Log.d("GET-POPULAR-FAILURE", t.getMessage());
            }
        });
    }
}
