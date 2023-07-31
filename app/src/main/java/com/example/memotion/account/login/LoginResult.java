package com.example.memotion.account.login;

public interface LoginResult {
    void loginSuccess(int code, LoginResponse.Result result);
    void loginFailure(int code, String message);
}
