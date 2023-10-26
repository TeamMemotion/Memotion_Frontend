package com.example.memotion.diary.post.emotion;

public interface PostEmotionResult {
    void postEmotionSuccess(int code, Long result);
    void postEmotionFailure(int code, String message);
}
