package com.example.memotion.route.patch.routedetail;

import static com.example.memotion.RetrofitClient.errorParsing;
import static com.example.memotion.RetrofitClient.getClient;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.memotion.RetrofitClient;
import com.example.memotion.route.post.routedetail.PostRouteDetailRequest;
import com.example.memotion.route.post.routedetail.PostRouteDetailResponse;
import com.example.memotion.route.post.routedetail.PostRouteDetailResult;
import com.example.memotion.route.post.routedetail.PostRouteDetailRetrofitInterface;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatchRouteDetailService {
    private PatchRouteDetailResult patchRouteDetailResult;

    public void setPatchRouteDetailResult(PatchRouteDetailResult patchRouteDetailResult) {
        this.patchRouteDetailResult = patchRouteDetailResult;
    }

    public void patchRouteDetail(Long routeDetailId, PatchRouteDetailRequest patchRouteDetailRequest) {
        PatchRouteDetailRetrofitInterface patchRouteDetailService = getClient().create(PatchRouteDetailRetrofitInterface.class);
        patchRouteDetailService.patchRouteDetail(routeDetailId, patchRouteDetailRequest).enqueue(new Callback<PatchRouteDetailResponse>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<PatchRouteDetailResponse> call, Response<PatchRouteDetailResponse> response) {
                Log.d("PATCH-ROUTE-DETAIL-SUCCESS", response.toString());

                if(response.isSuccessful()) {
                    if(response.body().getCode() == 1000) {
                        patchRouteDetailResult.patchRouteDetailSuccess(response.body().getCode(), response.body().getResult());
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
                            case 2002:
                                patchRouteDetailResult.patchRouteDetailFailure(errorResponse.getCode(), errorResponse.getMessage());
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<PatchRouteDetailResponse> call, Throwable t) {
                Log.d("PATCH-ROUTE-DETAIL-FAILURE", t.getMessage());
            }
        });
    }
}
