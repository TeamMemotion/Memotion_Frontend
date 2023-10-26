package com.example.memotion.account.login;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginRetrofitInterface {
    @POST("member/kakao")
    Call<LoginResponse> kakaoLogin(@Body LoginRequest.KakaoLogin kakaoLogin);

    @POST("member/login")
    Call<LoginResponse> login(@Body LoginRequest login);
}