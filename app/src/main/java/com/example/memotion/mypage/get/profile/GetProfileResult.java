package com.example.memotion.mypage.get.profile;

public interface GetProfileResult {
    void getProfileSuccess(int code, GetProfileResponse.Result result);
    void getProfileFailure(int code, String message);
}
