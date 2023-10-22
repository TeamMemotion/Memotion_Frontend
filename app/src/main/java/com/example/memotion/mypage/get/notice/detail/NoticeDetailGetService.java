package com.example.memotion.mypage.get.notice.detail;

import static com.example.memotion.RetrofitClient.errorParsing;
import static com.example.memotion.RetrofitClient.getClient;

import android.util.Log;

import com.example.memotion.RetrofitClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NoticeDetailGetService {

    private NoticeDetailGetResult noticeDetailGetResult;

    public void setNoticeDetailGetResult(NoticeDetailGetResult noticeDetailGetResult) {
        this.noticeDetailGetResult = noticeDetailGetResult;
    }

    public void getNoticeDetail(Long noticeId) {
        NoticeDetailGetRetrofitInterface getNoticeDetailService = getClient().create(NoticeDetailGetRetrofitInterface.class);
        getNoticeDetailService.getNoticeDetail(noticeId).enqueue(new Callback<NoticeDetailGetResponse>() {
            @Override
            public void onResponse(Call<NoticeDetailGetResponse> call, Response<NoticeDetailGetResponse> response) {
                Log.d("GET-N-DETAIL-SUCCESS", response.toString());

                if(response.isSuccessful()) {
                    if(response.body().getCode() == 1000) {
                        noticeDetailGetResult.getNoticeDetailSuccess(response.body().getCode(), response.body().getResult());
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
                                noticeDetailGetResult.getNoticeDetailFailure(errorResponse.getCode(), errorResponse.getMessage());
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<NoticeDetailGetResponse> call, Throwable t) {
                Log.d("GET-N-DETAIL-FAILURE", t.getMessage());
            }
        });
    }
}
