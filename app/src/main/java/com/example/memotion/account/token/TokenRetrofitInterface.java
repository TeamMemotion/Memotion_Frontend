package com.example.memotion.account.token;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface TokenRetrofitInterface {
    @POST("member/regenerate-token")
    Call<TokenResponse> regenerateToken(@Header("Authorization") String refreshToken);
}
