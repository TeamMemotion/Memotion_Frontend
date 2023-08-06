package com.example.memotion.account.auth.findPwd;

public interface FindPwdPostResult {
    void findPwdSuccess(int code, String result);
    void findPwdFailure(int code, String message);
}
