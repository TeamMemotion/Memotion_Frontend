package com.example.memotion.account.token;


public interface TokenResult {
    void regenerateTokenSuccess(int code, TokenResponse.Result result);
    void regenerateTokenFailure(int code, String message);
}
