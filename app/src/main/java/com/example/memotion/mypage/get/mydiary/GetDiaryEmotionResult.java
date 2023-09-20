package com.example.memotion.mypage.get.mydiary;

import java.util.ArrayList;

public interface GetDiaryEmotionResult {
    void getDiaryEmotionSuccess (int code, ArrayList<GetDiaryEmotionResponse.Result> result);
    void getDiaryEmotionFailure (int code, String message);
}
