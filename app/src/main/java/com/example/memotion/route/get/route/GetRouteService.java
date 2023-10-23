package com.example.memotion.route.get.route;

import static com.example.memotion.RetrofitClient.errorParsing;
import static com.example.memotion.RetrofitClient.getClient;

import android.util.Log;

import com.example.memotion.RetrofitClient;
import com.example.memotion.diary.get.emotion.GetEmotionResponse;
import com.example.memotion.diary.get.emotion.GetEmotionResult;
import com.example.memotion.diary.get.emotion.GetEmotionRetrofitInterface;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetRouteService {
    private GetRouteResult getRouteResult;

    public void setGetRouteResult(GetRouteResult getRouteResult) {
        this.getRouteResult = getRouteResult;
    }

    public void getRoute(Long routeId) {
        GetRouteRetrofitInterface getRouteService = getClient().create(GetRouteRetrofitInterface.class);
        getRouteService.getRoute(routeId).enqueue(new Callback<GetRouteResponse>() {

            @Override
            public void onResponse(Call<GetRouteResponse> call, Response<GetRouteResponse> response) {
                Log.d("GET-ROUTE-SUCCESS", response.toString());

                if(response.isSuccessful()) {
                    if(response.body().getCode() == 1000) {
                        getRouteResult.getRouteSuccess(response.body().getCode(), response.body().getResult());
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
                                getRouteResult.getRouteFailure(errorResponse.getCode(), errorResponse.getMessage());
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetRouteResponse> call, Throwable t) {
                Log.d("GET-Route-FAILURE", t.getMessage());
            }
        });
    }
}
