package com.example.memotion.mypage.get.notice.detail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NoticeDetailGetRetrofitInterface {
    @GET("notice/{noticeId}")
    Call<NoticeDetailGetResponse> getNoticeDetail(@Path(value = "noticeId") Long noticeId);
}
