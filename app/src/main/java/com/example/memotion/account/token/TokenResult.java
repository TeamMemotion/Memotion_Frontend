package com.example.memotion.account.token;


import java.io.IOException;

import okhttp3.Interceptor;

public interface TokenResult {
    void regenerateTokenSuccess(int code, TokenResponse.Result result);
    void regenerateTokenFailure(int code, String message);
}
