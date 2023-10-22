package com.example.memotion.mypage.get.notice.all;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NoticeAllGetRetrofitInterface {
    @GET("notice")
    Call<NoticeAllGetResponse> getNoticeAll();
}
