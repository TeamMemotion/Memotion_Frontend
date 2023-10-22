package com.example.memotion.mypage.patch.profile;

public interface ProfilePatchResult {
    void patchProfileSuccess(int code, String result);
    void patchProfileFailure(int code, String message);
}
