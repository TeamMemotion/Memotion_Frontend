package com.example.memotion.diary.get.emotion;

import java.util.ArrayList;

public interface GetEmotionResult {
    void getEmotionSuccess(int code, ArrayList<GetEmotionResponse.Result> result);
    void getEmotionFailure(int code, String message);
}
