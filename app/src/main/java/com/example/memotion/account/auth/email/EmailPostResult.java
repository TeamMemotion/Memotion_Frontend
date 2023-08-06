package com.example.memotion.account.auth.email;

import com.example.memotion.account.login.LoginResponse;

public interface EmailPostResult {
    void emailSuccess(int code, String result);
    void emailFailure(int code, String message);
}
