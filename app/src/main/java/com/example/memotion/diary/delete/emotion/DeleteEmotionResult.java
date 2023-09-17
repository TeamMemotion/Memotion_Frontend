package com.example.memotion.diary.delete.emotion;

public interface DeleteEmotionResult {
    void deleteEmotionSuccess(int code, Long result);
    void deleteEmotionFailure(int code, String message);
}
