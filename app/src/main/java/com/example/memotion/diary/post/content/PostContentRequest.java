package com.example.memotion.diary.post.content;

import com.google.gson.annotations.SerializedName;

public class PostContentRequest {
    @SerializedName(value = "title")
    private String title;
    @SerializedName(value = "content")
    private String content;
    @SerializedName(value = "createdDate")
    private String createdDate;

    public PostContentRequest(String title, String content, String createdDate) {
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
    }
}