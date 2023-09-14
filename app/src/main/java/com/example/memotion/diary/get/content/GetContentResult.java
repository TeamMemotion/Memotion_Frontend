package com.example.memotion.diary.get.content;

public interface GetContentResult {
    void getContentSuccess(int code, GetContentResponse.Result result);
    void getContentFailure(int code, String message);
}
