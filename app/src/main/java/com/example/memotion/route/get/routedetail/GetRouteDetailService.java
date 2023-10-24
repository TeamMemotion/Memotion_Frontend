package com.example.memotion.route.get.routedetail;

import static com.example.memotion.RetrofitClient.errorParsing;
import static com.example.memotion.RetrofitClient.getClient;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.memotion.RetrofitClient;
import com.example.memotion.route.get.route.GetRouteResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetRouteDetailService {
    private GetRouteDetailResult getRouteDetailResult;

    public void setGetRouteDetailResult(GetRouteDetailResult getRouteDetailResult) {
        this.getRouteDetailResult = getRouteDetailResult;
    }

    public void getRouteDetail(Long routeDetailId) {
        GetRouteDetailRetrofitInterface getRouteDetailService = getClient().create(GetRouteDetailRetrofitInterface.class);
        getRouteDetailService.getRouteDetail(routeDetailId).enqueue(new Callback<GetRouteDetailResponse>() {

            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<GetRouteDetailResponse> call, Response<GetRouteDetailResponse> response) {
                Log.d("GET-ROUTE-DETAIL-SUCCESS", response.toString());

                if(response.isSuccessful()) {
                    if(response.body().getCode() == 1000) {
                        getRouteDetailResult.getRouteDetailSuccess(response.body().getCode(), response.body().getResult());
                    }
                } else {
                    //400이상 에러시 response.body가 null로 처리됨. 따라서 errorBody로 받아야함.
                    try {
                        String errorBody = response.errorBody().string();
                        RetrofitClient.ErrorResponse errorResponse = errorParsing(errorBody);

                        Log.d("ErrorBody : ", errorBody);
                        Log.d("errorCode: ", String.valueOf(errorResponse.getCode()));
                        Log.d("errorMessage: ", errorResponse.getMessage()!=null && !errorResponse.getMessage().isEmpty() ? errorResponse.getMessage() : " ");

                        switch (errorResponse.getCode()) {
                            case 500:
                            case 2001:
                                getRouteDetailResult.getRouteDetailFailure(errorResponse.getCode(), errorResponse.getMessage());
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<GetRouteDetailResponse> call, Throwable t) {
                Log.d("GET-ROUTE-DETAIL-FAILURE", t.getMessage());
            }
        });
    }
}
