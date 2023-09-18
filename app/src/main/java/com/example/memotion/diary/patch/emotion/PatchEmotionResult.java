package com.example.memotion.diary.patch.emotion;

public interface PatchEmotionResult {
    void patchEmotionSuccess(int code, PatchEmotionResponse.Result result);
    void patchEmotionFailure(int code, String message);
}
