package com.example.memotion.account.auth.signup;

public interface SignUpPostResult {
    void signUpSuccess(int code, SignUpPostResponse.Result result);
    void signUpFailure(int code, String message);
}
