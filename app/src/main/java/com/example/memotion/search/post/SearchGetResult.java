package com.example.memotion.search.post;

import java.util.ArrayList;

public interface SearchGetResult {
    void getSearchSuccess(int code, ArrayList<SearchGetResponse.Result> result);
    void getSearchFailure(int code, String message);
}
