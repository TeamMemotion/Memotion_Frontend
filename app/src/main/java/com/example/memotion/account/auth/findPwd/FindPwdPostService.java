package com.example.memotion.account.auth.findPwd;

import static android.content.ContentValues.TAG;
import static com.example.memotion.NetworkModule.errorParsing;
import static com.example.memotion.NetworkModule.getRetrofit;

import android.util.Log;

import com.example.memotion.NetworkModule;

import java.io.IOException;
import java.time.LocalDate;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindPwdPostService {
    private FindPwdPostResult findPwdPostResult;

    public void setFindPwdPostResult(FindPwdPostResult findPwdPostResult) {
        this.findPwdPostResult = findPwdPostResult;
    }

    public void findPwd(FindPwdPostRequest findPwdPostRequest) {
        FindPwdPostRetrofitInterface pwdFindService = getRetrofit().create(FindPwdPostRetrofitInterface.class);
        pwdFindService.findPwd(findPwdPostRequest).enqueue(new Callback<FindPwdPostResponse>() {
            @Override
            public void onResponse(Call<FindPwdPostResponse> call, Response<FindPwdPostResponse> response) {
                Log.d("FIND-PWD-SUCCESS", response.toString());
                //FindPwdPostResponse resp = response.body();

                if(response.isSuccessful()) {
                    if(response.body().getCode() == 1000) {
                        findPwdPostResult.findPwdSuccess(response.body().getCode(), response.body().getResult());
                    }
                } else {        //400이상 에러시 response.body가 null로 처리됨. 따라서 errorBody로 받아야함.
                    try {
                        String errorBody = response.errorBody().string();
                        NetworkModule.ErrorResponse errorResponse = errorParsing(errorBody);

                        Log.d("ErrorBody : ", errorBody);
                        Log.d("errorCode: ", String.valueOf(errorResponse.getCode()));
                        Log.d("errorMessage: ", errorResponse.getMessage());

                        switch (errorResponse.getCode()) {
                            case 2001:
                                findPwdPostResult.findPwdFailure(errorResponse.getCode(), errorResponse.getMessage());
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<FindPwdPostResponse> call, Throwable t) {
                Log.d("FIND-PWD-FAILURE", t.getMessage());
            }
        });
    }
}
