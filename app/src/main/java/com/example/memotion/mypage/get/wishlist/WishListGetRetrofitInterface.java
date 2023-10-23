package com.example.memotion.mypage.get.wishlist;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WishListGetRetrofitInterface {
    @GET("route-like")
    Call<WishListGetResponse> getWishList();
}
