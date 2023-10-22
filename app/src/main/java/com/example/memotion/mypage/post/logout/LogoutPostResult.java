package com.example.memotion.mypage.post.logout;

public interface LogoutPostResult {
    void logoutSuccess(int code, String result);
    void logoutFailure(int code, String message);
}
