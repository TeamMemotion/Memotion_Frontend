package com.example.memotion.search.get;

import static com.example.memotion.RetrofitClient.errorParsing;
import static com.example.memotion.RetrofitClient.getClient;

import android.util.Log;

import com.example.memotion.RetrofitClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchGetService {

    private SearchGetResult searchGetResult;

    public void setSearchGetResult(SearchGetResult searchGetResult) {
        this.searchGetResult = searchGetResult;
    }

    public void getSearch(String filter, Double latitude, Double longitude) {
        SearchGetRetrofitInterface getSearchService = getClient().create(SearchGetRetrofitInterface.class);

        getSearchService.getSearch(filter, latitude, longitude).enqueue(new Callback<SearchGetResponse>() {
            @Override
            public void onResponse(Call<SearchGetResponse> call, Response<SearchGetResponse> response) {
                Log.d("GET-SEARCH-SUCCESS", response.toString());

                if(response.isSuccessful()) {
                    if(response.body().getCode() == 1000) {
                        searchGetResult.getSearchSuccess(response.body().getCode(), response.body().getResult());
                    }
                } else {
                    //400이상 에러시 response.body가 null로 처리됨. 따라서 errorBody로 받아야함.
                    try {
                        String errorBody = response.errorBody().string();
                        RetrofitClient.ErrorResponse errorResponse = errorParsing(errorBody);

                        Log.d("ErrorBody : ", errorBody);
                        Log.d("errorCode: ", String.valueOf(errorResponse.getCode()));
                        Log.d("errorMessage: ", errorResponse.getMessage());

                        switch (errorResponse.getCode()) {
                            case 500:
                            case 2015:
                                searchGetResult.getSearchFailure(errorResponse.getCode(), errorResponse.getMessage());
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchGetResponse> call, Throwable t) {
                Log.d("GET-SEARCH-FAILURE", t.getMessage());
            }
        });
    }
}
