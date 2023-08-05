package com.example.memotion.account.auth.email;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface EmailPostRetrofitInterface {
    @POST("member/check-email")
    Call<EmailPostResponse> emailAuthentication(@Body EmailPostRequest emailPostRequest);
}
