package com.example.memotion.route.get.localGuide;

import java.util.ArrayList;

public interface LocalGuideGetResult {
    void getLocalGuideSuccess(int code, ArrayList<LocalGuideGetResponse.Result> result);
    void getLocalGuideFailure(int code, String message);
}
