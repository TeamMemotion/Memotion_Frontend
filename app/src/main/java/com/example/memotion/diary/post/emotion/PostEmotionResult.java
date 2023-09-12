package com.example.memotion.diary.post.emotion;

import com.example.memotion.account.login.LoginResponse;

public interface PostEmotionResult {
    void postEmotionSuccess(int code, Long result);
    void postEmotionFailure(int code, String message);
}
