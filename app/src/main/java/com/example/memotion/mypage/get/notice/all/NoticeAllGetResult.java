package com.example.memotion.mypage.get.notice.all;

import java.util.ArrayList;

public interface NoticeAllGetResult {
    void getNoticeAllSuccess (int code, ArrayList<NoticeAllGetResponse.Result> result);
    void getNoticeAllFailure (int code, String message);
}
