package com.example.memotion.mypage.get.wishlist;

import static com.example.memotion.RetrofitClient.errorParsing;
import static com.example.memotion.RetrofitClient.getClient;

import android.util.Log;

import com.example.memotion.RetrofitClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishListGetService {

    private WishListGetResult wishListGetResult;

    public void setWishListGetResult(WishListGetResult wishListGetResult) {
        this.wishListGetResult = wishListGetResult;
    }

    public void getWishList() {
        WishListGetRetrofitInterface getWishListService = getClient().create(WishListGetRetrofitInterface.class);

        getWishListService.getWishList().enqueue(new Callback<WishListGetResponse>() {
            @Override
            public void onResponse(Call<WishListGetResponse> call, Response<WishListGetResponse> response) {
                Log.d("GET-WISHLIST-SUCCESS", response.toString());

                if(response.isSuccessful()) {
                    if(response.body().getCode() == 1000) {
                        wishListGetResult.getWishListSuccess(response.body().getCode(), response.body().getResult());
                    }
                } else {
                    //400이상 에러시 response.body가 null로 처리됨. 따라서 errorBody로 받아야함.
                    try {
                        String errorBody = response.errorBody().string();
                        RetrofitClient.ErrorResponse errorResponse = errorParsing(errorBody);

                        Log.d("ErrorBody : ", errorBody);
                        Log.d("errorCode: ", String.valueOf(errorResponse.getCode()));
                        Log.d("errorMessage: ", errorResponse.getMessage() != null && !errorResponse.getMessage().isEmpty() ? errorResponse.getMessage() : " ");

                        switch (errorResponse.getCode()) {
                            case 500:
                            case 2017:
                                wishListGetResult.getWishListFailure(errorResponse.getCode(), errorResponse.getMessage());
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<WishListGetResponse> call, Throwable t) {
                Log.d("GET-WISHLIST-FAILURE", t.getMessage());
            }
        });
    }
}
