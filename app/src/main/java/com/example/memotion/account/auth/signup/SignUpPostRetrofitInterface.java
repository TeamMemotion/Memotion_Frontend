package com.example.memotion.account.auth.signup;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignUpPostRetrofitInterface {
    @POST("member/signup")
    Call<SignUpPostResponse> signUp(@Body SignUpPostRequest signUpPostRequest);
}
