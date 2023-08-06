package com.example.memotion.account.auth.findPwd;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface FindPwdPostRetrofitInterface {
    @POST("member/check-password")
    Call<FindPwdPostResponse> findPwd(@Body FindPwdPostRequest findPwdPostRequest);
}
