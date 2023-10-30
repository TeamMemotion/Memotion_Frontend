package com.example.memotion.route.get.routedetailList;

import static com.example.memotion.RetrofitClient.errorParsing;
import static com.example.memotion.RetrofitClient.getClient;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.memotion.RetrofitClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetRouteDetailListService {

    public static GetRouteDetailListResult getRouteDetailListResult;

    public void setGetRouteDetailListResult(GetRouteDetailListResult getRouteDetailListResult) {
        this.getRouteDetailListResult = getRouteDetailListResult;
    }

    public void getRouteDetailList(Long id, String selectDate){
        GetRouteDetailListRetrofitInterface getRouteDetailListRetrofitInterface = getClient().create(GetRouteDetailListRetrofitInterface.class);
        getRouteDetailListRetrofitInterface.getRouteDetailList(id, selectDate).enqueue(new Callback<GetRouteDetailListResponse>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<GetRouteDetailListResponse> call, Response<GetRouteDetailListResponse> response) {
                Log.d("GET-ROUTE-DETAIL-LIST-SUCCESS", response.toString());
                if(response.isSuccessful()) {
                    if(response.body().getCode() == 1000) {
                        getRouteDetailListResult.getRouteDetailListSuccess(response.body().getCode(), response.body().getResult());
                    }
                }else {
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
                                getRouteDetailListResult.getRouteDetailListFailure(errorResponse.getCode(), errorResponse.getMessage());
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<GetRouteDetailListResponse> call, Throwable t) {
                Log.d("GET-ROUTE-DETAIL-LIST-SUCCESS", t.getMessage());
            }
        });
    }
}
