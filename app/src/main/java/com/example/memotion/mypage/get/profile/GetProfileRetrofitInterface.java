package com.example.memotion.mypage.get.profile;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetProfileRetrofitInterface {
    @GET("member/profile")
    Call<GetProfileResponse> getProfile();
}
