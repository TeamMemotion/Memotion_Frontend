package com.example.memotion.mypage.get.notice.all;

import static com.example.memotion.RetrofitClient.errorParsing;
import static com.example.memotion.RetrofitClient.getClient;

import android.util.Log;

import com.example.memotion.RetrofitClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeAllGetService {

    private NoticeAllGetResult noticeAllGetResult;

    public void setNoticeAllGetResult(NoticeAllGetResult noticeAllGetResult) {
        this.noticeAllGetResult = noticeAllGetResult;
    }

    public void getNoticeAll() {
        NoticeAllGetRetrofitInterface getNoticeAllService = getClient().create(NoticeAllGetRetrofitInterface.class);

        getNoticeAllService.getNoticeAll().enqueue(new Callback<NoticeAllGetResponse>() {
            @Override
            public void onResponse(Call<NoticeAllGetResponse> call, Response<NoticeAllGetResponse> response) {
                Log.d("GET-NOTICE-ALL-SUCCESS", response.toString());

                if(response.isSuccessful()) {
                    if(response.body().getCode() == 1000) {
                        noticeAllGetResult.getNoticeAllSuccess(response.body().getCode(), response.body().getResult());
                    }
                } else {
                    //400이상 에러시 response.body가 null로 처리됨. 따라서 errorBody로 받아야함.
                    try {
                        String errorBody = response.errorBody().string();
                        RetrofitClient.ErrorResponse errorResponse = errorParsing(errorBody);

                        Log.d("ErrorBody : ", errorBody);
                        Log.d("errorCode: ", String.valueOf(errorResponse.getCode()));
                        Log.d("errorMessage: ", errorResponse.getMessage()!=null && !errorResponse.getMessage().isEmpty() ? errorResponse.getMessage() : " ");

                        switch (errorResponse.getCode()) {
                            case 500:
                            case 2001:
                                noticeAllGetResult.getNoticeAllFailure(errorResponse.getCode(), errorResponse.getMessage());
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<NoticeAllGetResponse> call, Throwable t) {
                Log.d("GET-NOTICE-ALL-FAILURE", t.getMessage());
            }
        });
    }
}
