package com.example.memotion.mypage.post.logout;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LogoutPostRetrofitInterface {
    @POST("member/logout")
    Call<LogoutPostResponse> logout(@Header("Authorization") String refreshToken);
}
