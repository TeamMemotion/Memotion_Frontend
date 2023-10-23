package com.example.memotion.route.get.localGuide.latest;

import static com.example.memotion.RetrofitClient.errorParsing;
import static com.example.memotion.RetrofitClient.getClient;

import android.util.Log;

import com.example.memotion.RetrofitClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocalGuideGetService {

    private LocalGuideGetResult localGuideGetResult;

    public void setLocalGuideGetResult(LocalGuideGetResult localGuideGetResult) {
        this.localGuideGetResult = localGuideGetResult;
    }

    public void getLocalGuide() {
        LocalGuideGetRetrofitInterface getLocalGuideService = getClient().create(LocalGuideGetRetrofitInterface.class);

        getLocalGuideService.getLocalGuide().enqueue(new Callback<LocalGuideGetResponse>() {
            @Override
            public void onResponse(Call<LocalGuideGetResponse> call, Response<LocalGuideGetResponse> response) {
                Log.d("GET-LOCALGUIDE-SUCCESS", response.toString());

                if(response.isSuccessful()) {
                    if(response.body().getCode() == 1000) {
                        localGuideGetResult.getLocalGuideSuccess(response.body().getCode(), response.body().getResult());
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
                                localGuideGetResult.getLocalGuideFailure(errorResponse.getCode(), errorResponse.getMessage());
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<LocalGuideGetResponse> call, Throwable t) {
                Log.d("GET-LOCALGUIDE-FAILURE", t.getMessage());
            }
        });
    }
}
