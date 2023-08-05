package com.example.memotion.account.login;

import static com.example.memotion.NetworkModule.errorParsing;
import static com.example.memotion.NetworkModule.getRetrofit;

import android.util.Log;

import com.example.memotion.NetworkModule;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginService {
    private LoginResult loginResult;

    public void setLoginResult(LoginResult loginResult) {
        this.loginResult = loginResult;
    }

    // 일반 로그인
    public void login(LoginRequest loginRequest) {
        LoginRetrofitInterface loginService = getRetrofit().create(LoginRetrofitInterface.class);
        loginService.login(loginRequest).enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d("LOGIN-SUCCESS", response.toString());
                //FindPwdPostResponse resp = response.body();

                if(response.isSuccessful()) {
                    if(response.body().getCode() == 1000) {
                        loginResult.loginSuccess(response.body().getCode(), response.body().getResult());
                    }
                } else {        //400이상 에러시 response.body가 null로 처리됨. 따라서 errorBody로 받아야함.
                    try {
                        String errorBody = response.errorBody().string();
                        NetworkModule.ErrorResponse errorResponse = errorParsing(errorBody);

                        Log.d("ErrorBody : ", errorBody);
                        Log.d("errorCode: ", String.valueOf(errorResponse.getCode()));
                        Log.d("errorMessage: ", errorResponse.getMessage());

                        switch (errorResponse.getCode()) {
                            case 500:
                            case 2001:
                                loginResult.loginFailure(errorResponse.getCode(), errorResponse.getMessage());
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("LOGIN-FAILURE", t.getMessage());
            }
        });
    }
    
    // 카카오 로그인
    public void kakaoLogin(LoginRequest.KakaoLogin loginRequest) {
         LoginRetrofitInterface loginService = getRetrofit().create(LoginRetrofitInterface.class);
         loginService.kakaoLogin(loginRequest).enqueue(new Callback<LoginResponse>() {
             // 성공한 경우
             @Override
             public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                 Log.d("KAKAO-LOGIN SUCCESS", response.toString());
                 LoginResponse resp = response.body();

                 switch(resp.getCode()) {
                     case 1000:
                         loginResult.loginSuccess(resp.getCode(), resp.getResult());
                         break;
                     default:
                         loginResult.loginFailure(resp.getCode(), resp.getMessage());
                         break;
                 }
             }

             // 실패한 경우
             @Override
             public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("KAKAO-LOGIN FAILURE", t.getMessage().toString());
             }
         });
    }
}
