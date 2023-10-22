package com.example.memotion.mypage.post.logout;

import static com.example.memotion.NetworkModule.getRetrofit;
import static com.example.memotion.RetrofitClient.errorParsing;

import android.util.Log;

import com.example.memotion.RetrofitClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogoutPostService {
    private String result;

    private LogoutPostResult logoutPostResult;

    public void setLogoutPostResult(LogoutPostResult logoutPostResult) {
        this.logoutPostResult = logoutPostResult;
    }

    public void logout() {
        LogoutPostRetrofitInterface logoutService = getRetrofit().create(LogoutPostRetrofitInterface.class);

        logoutService.logout("Bearer " + RetrofitClient.getRefreshToken()).enqueue(new Callback<LogoutPostResponse>() {
            @Override
            public void onResponse(Call<LogoutPostResponse> call, Response<LogoutPostResponse> response) {
                Log.d("LOGOUT-SUCCESS", response.toString());

                if(response.isSuccessful()) {
                    if(response.body().getCode() == 1000) {
                        logoutPostResult.logoutSuccess(response.body().getCode(), response.body().getResult());
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
                            case 2002:
                            case 2012:
                                logoutPostResult.logoutFailure(errorResponse.getCode(), errorResponse.getMessage());
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<LogoutPostResponse> call, Throwable t) {
                Log.d("LOGOUT-FAILURE", t.getMessage());
            }
        });
    }
}
