package com.example.memotion.diary.post.content;

public interface PostContentResult {
    void postContentSuccess(int code, Long result);
    void postContentFailure(int code, String message);
}