package com.example.memotion.mypage.get.wishlist;

import java.util.ArrayList;

public interface WishListGetResult {
    void getWishListSuccess(int code, ArrayList<WishListGetResponse.Result> result);
    void getWishListFailure(int code, String message);
}
