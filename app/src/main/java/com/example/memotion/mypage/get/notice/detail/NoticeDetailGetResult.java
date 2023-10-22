package com.example.memotion.mypage.get.notice.detail;

import com.example.memotion.mypage.get.notice.all.NoticeAllGetResponse;

public interface NoticeDetailGetResult {
    void getNoticeDetailSuccess(int code, NoticeAllGetResponse.Result result);
    void getNoticeDetailFailure(int code, String message);
}
