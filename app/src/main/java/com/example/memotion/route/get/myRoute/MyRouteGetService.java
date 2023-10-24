package com.example.memotion.route.get.myRoute;

import static com.example.memotion.RetrofitClient.errorParsing;
import static com.example.memotion.RetrofitClient.getClient;

import android.util.Log;

import com.example.memotion.RetrofitClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyRouteGetService {

    private MyRouteGetResult myRouteGetResult;

    public void setMyRouteGetResult(MyRouteGetResult myRouteGetResult) {
        this.myRouteGetResult = myRouteGetResult;
    }

    public void getMyRoute() {
        MyRouteGetRetrofitInterface getMyRouteService = getClient().create(MyRouteGetRetrofitInterface.class);

        getMyRouteService.getMyRoute().enqueue(new Callback<MyRouteGetResponse>() {
            @Override
            public void onResponse(Call<MyRouteGetResponse> call, Response<MyRouteGetResponse> response) {
                Log.d("GET-MYROUTE-SUCCESS", response.toString());

                if(response.isSuccessful()) {
                    if(response.body().getCode() == 1000) {
                        myRouteGetResult.getMyRouteSuccess(response.body().getCode(), response.body().getResult());
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
                                myRouteGetResult.getMyRouteFailure(errorResponse.getCode(), errorResponse.getMessage());
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MyRouteGetResponse> call, Throwable t) {
                Log.d("GET-MYROUTE-FAILURE", t.getMessage());
            }
        });
    }
}
