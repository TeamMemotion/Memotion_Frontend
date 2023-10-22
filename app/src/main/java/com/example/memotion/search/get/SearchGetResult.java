package com.example.memotion.search.get;

import java.util.ArrayList;

public interface SearchGetResult {
    void getSearchSuccess(int code, ArrayList<SearchGetResponse.Result> result);
    void getSearchFailure(int code, String message);
}
