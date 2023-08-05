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
                FindPwdPostResponse resp = response.body();

                if(resp == null) {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.d("ErrorBody : ", errorBody);
                        Log.d("errorCode: ", String.valueOf(errorParsing(errorBody).getCode()));
                        Log.d("errorMessage: ", errorParsing(errorBody).getMessage());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(Integer.valueOf(resp.getCode()) != null) {
                    switch (resp.getCode()) {
                        case 1000:
                            findPwdPostResult.findPwdSuccess(resp.getCode(), resp.getResult());
                            break;
                        default:
                            findPwdPostResult.findPwdFailure(resp.getCode(), resp.getMessage());
                            break;
                    }
                } else {
                    Log.d("onfailure code: ", "응답 실패");
                }
            }

            @Override
            public void onFailure(Call<FindPwdPostResponse> call, Throwable t) {
                Log.d("FIND-PWD-FAILURE", t.getMessage());
            }
        });
    }
}
