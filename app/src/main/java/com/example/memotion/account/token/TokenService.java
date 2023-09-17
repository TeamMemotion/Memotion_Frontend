package com.example.memotion.account.token;

import static com.example.memotion.NetworkModule.getRetrofit;
import static com.example.memotion.RetrofitClient.errorParsing;

import android.util.Log;

import com.example.memotion.NetworkModule;
import com.example.memotion.RetrofitClient;

import java.io.IOException;

import okhttp3.Interceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TokenService {
    private static String TAG = "TokenService";
    private TokenResult tokenResult;

    public void setTokenResult(TokenResult tokenResult) {
        this.tokenResult = tokenResult;
    }

    // 토큰 재발급
    public void refreshToken() {
        TokenRetrofitInterface tokenService = getRetrofit().create(TokenRetrofitInterface.class);
        tokenService.regenerateToken("Bearer " + RetrofitClient.getRefreshToken()).enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                Log.d("REFRESH-SUCCESS", response.toString());

                if(response.isSuccessful()) {
                    if(response.body().getCode() == 1000) {
                        tokenResult.regenerateTokenSuccess(response.body().getCode(), response.body().getResult());
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
                            case 2001:
                                // 어진이한테 에러처리 물어보고 추가하기
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.d(TAG, "토큰 갱신에 실패했습니다.");
                Log.d(TAG, t.getMessage());

                // TO DO : 23.09.15 refresh도 만료된 경우 -> 로그아웃 코드 추가
            }
        });
    }
}
