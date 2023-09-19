package com.example.memotion.home.get.emotions;

import java.util.ArrayList;

public interface GetEmotionsResult {
    void getEmotionsSuccess(int code, ArrayList<GetEmotionsResponse.Result> result);
    void getEmotionFailure(int code, String message);
}
