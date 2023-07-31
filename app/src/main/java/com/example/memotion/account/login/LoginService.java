package com.example.memotion.account.login;

import static com.example.memotion.NetworkModule.getRetrofit;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginService {
    private LoginResult loginResult;

    public void setLoginResponse(LoginResult loginResult) {
        this.loginResult = loginResult;
    }

    // 일반 로그인
    public void login(LoginRequest loginRequest) {
        LoginRetrofitInterface loginService = getRetrofit().create(LoginRetrofitInterface.class);
        loginService.login(loginRequest).enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

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
